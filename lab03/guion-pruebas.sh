#!/usr/bin/env bash
# Guion de pruebas del Lab 03.
# Se ejecuta igual antes (Parte 1) y después (Parte 2) del refactor por módulos:
# si el refactor no introdujo regresiones, la salida debe ser idéntica.
#
#   uso:  ./guion-pruebas.sh [puerto]    (por defecto 8081)

BASE="http://localhost:${1:-8081}"

req() { # req <metodo> <ruta> [body]
  if [ -n "$3" ]; then
    curl -sS -X "$1" "$BASE$2" -H 'Content-Type: application/json' -d "$3" -w '\nHTTP %{http_code}\n'
  else
    curl -sS -X "$1" "$BASE$2" -w '\nHTTP %{http_code}\n'
  fi
}

echo "===== 1) POST /api/programas ====="
req POST /api/programas '{"codigo":"PROG-ING","nombre":"Ingeniería de Software"}'

echo
echo "===== 2) POST /api/asignaturas (con programaId) ====="
PROG_ID=$(curl -sS "$BASE/api/programas?page=0&size=100" | python3 -c "
import sys,json
d=json.load(sys.stdin)['data']['content']
print([p['id'] for p in d if p['codigo']=='PROG-ING'][0])")
req POST /api/asignaturas "{\"codigo\":\"ASIG-IS3\",\"nombre\":\"Ingeniería de Software III\",\"creditos\":3,\"programaId\":$PROG_ID}"

echo
echo "===== 3) POST /api/grupos (asignaturaId, cupoMax=2) ====="
ASIG_ID=$(curl -sS "$BASE/api/asignaturas" | python3 -c "
import sys,json
d=json.load(sys.stdin)['data']
print([a['id'] for a in d if a['codigo']=='ASIG-IS3'][0])")
req POST /api/grupos "{\"codigoGrupo\":\"G-IS3-01\",\"cupoMax\":2,\"asignaturaId\":$ASIG_ID}"

GRUPO_ID=$(curl -sS "$BASE/api/grupos?page=0&size=100" | python3 -c "
import sys,json
d=json.load(sys.stdin)['data']['content']
print([g['id'] for g in d if g['codigoGrupo']=='G-IS3-01'][0])")

echo
echo "===== 4) POST /api/estudiantes (tres estudiantes) ====="
for n in 1 2 3; do
  req POST /api/estudiantes "{\"nombre\":\"Estudiante Lab03 $n\",\"email\":\"lab03.est$n@demo.com\"}"
done
EST=$(curl -sS "$BASE/api/estudiantes?page=0&size=1000" | python3 -c "
import sys,json
d=json.load(sys.stdin)['data']['content']
ids=[e['id'] for e in d if e['email'].startswith('lab03.est')]
print(' '.join(map(str,sorted(ids))))")
set -- $EST
E1=$1; E2=$2; E3=$3

echo
echo "===== 5) POST /api/matriculas  — matrícula 1 (debe pasar) ====="
req POST /api/matriculas "{\"estudianteId\":$E1,\"grupoId\":$GRUPO_ID}"

echo
echo "===== 6) POST /api/matriculas  — matrícula 2 (debe pasar, llena el cupo) ====="
req POST /api/matriculas "{\"estudianteId\":$E2,\"grupoId\":$GRUPO_ID}"

echo
echo "===== 7) POST /api/matriculas  — matrícula 3 (debe FALLAR por cupo) ====="
req POST /api/matriculas "{\"estudianteId\":$E3,\"grupoId\":$GRUPO_ID}"

echo
echo "===== 8) POST /api/matriculas  — duplicada (debe FALLAR por duplicado) ====="
req POST /api/matriculas "{\"estudianteId\":$E1,\"grupoId\":$GRUPO_ID}"

echo
echo "===== 9) POST /api/matriculas  — estudiante inexistente (404) ====="
req POST /api/matriculas "{\"estudianteId\":999999,\"grupoId\":$GRUPO_ID}"

echo
echo "===== 10) POST /api/matriculas  — grupo inexistente (404) ====="
req POST /api/matriculas "{\"estudianteId\":$E1,\"grupoId\":999999}"
