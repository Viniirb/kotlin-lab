#!/usr/bin/env bash
# Mostra quais módulos já estão concluídos (rodando a MESMA validação do CI). Pode demorar alguns minutos.
# Uso: scripts/progress.sh            (todos)
#      scripts/progress.sh 03 04      (só alguns)
cd "$(dirname "$0")/.."

selected=("$@")
done_count=0
total=0
while IFS='|' read -r id slug title; do
  if [ ${#selected[@]} -gt 0 ] && [[ ! " ${selected[*]} " =~ " $id " ]]; then continue; fi
  total=$((total + 1))
  if scripts/validate-module.sh "$id" >/dev/null 2>&1; then
    printf '✔ %s  %s\n' "$id" "$title"
    done_count=$((done_count + 1))
  else
    printf '· %s  %s\n' "$id" "$title"
  fi
done < scripts/modules.txt

echo
echo "$done_count de $total módulos concluídos."
