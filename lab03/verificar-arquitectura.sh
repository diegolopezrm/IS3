#!/usr/bin/env bash
# Verificación arquitectónica del Lab 03 (paso B7).
# La regla: el módulo matriculas NO puede inyectar repositorios de otros
# módulos; solo puede comunicarse con ellos a través de Ports.

SRC="src/main/java/co/edu/demoacademico"
MAT="$SRC/matriculas"
fallos=0

chk_ausente() { # chk_ausente <patron> <descripcion>
  if grep -rq "$1" "$MAT" 2>/dev/null; then
    echo "  FALLA  $2"
    grep -rn "$1" "$MAT" | sed 's/^/           /'
    fallos=$((fallos+1))
  else
    echo "  OK     $2"
  fi
}

chk_presente() { # chk_presente <patron> <descripcion>
  if grep -rq "$1" "$MAT" 2>/dev/null; then
    echo "  OK     $2"
  else
    echo "  FALLA  $2"
    fallos=$((fallos+1))
  fi
}

echo "Regla: matriculas no depende de repositorios ajenos, solo de Ports"
echo
chk_ausente  "import co\.edu\.demoacademico\.estudiantes\.repository" \
             "matriculas NO importa estudiantes.repository"
chk_ausente  "import co\.edu\.demoacademico\.grupos\.repository" \
             "matriculas NO importa grupos.repository"
chk_presente "import co\.edu\.demoacademico\.estudiantes\.port\.EstudianteQueryPort" \
             "matriculas SÍ importa EstudianteQueryPort"
chk_presente "import co\.edu\.demoacademico\.grupos\.port\.GrupoQueryPort" \
             "matriculas SÍ importa GrupoQueryPort"

echo
if [ "$fallos" -eq 0 ]; then
  echo "Resultado: se cumple la regla arquitectónica."
else
  echo "Resultado: $fallos violación(es) de la regla."
fi
exit $fallos
