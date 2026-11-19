#!/usr/bin/env bash
# Valida UM módulo: roda os testes dele e confere que não sobrou TODO("MNN...") nos arquivos.
# Uso: scripts/validate-module.sh 03
# Usado localmente e pelo GitHub Actions (.github/workflows/modules.yml): a mesma régua nos dois lugares.
set -euo pipefail
cd "$(dirname "$0")/.."

module="${1:?uso: scripts/validate-module.sh NN (ex.: 03)}"
G=./gradlew
darwin=false
[ "$(uname)" = "Darwin" ] && darwin=true

# Cada caso lista as tarefas Gradle que PROVAM o módulo. `--tests` filtra a tarefa imediatamente anterior.
case "$module" in
  00) tasks=(:lessons:test --tests "lab.lessons.LessonsSmokeTest") ;;
  01) tasks=(:lessons:test --tests "lab.exercises.Exercise1*" --tests "lab.exercises.Exercise2*") ;;
  02) tasks=(:lessons:test --tests "lab.exercises.Exercise3*") ;;
  03) tasks=(:core:jvmTest --tests "lab.taskmanager.PriorityTest" --tests "lab.taskmanager.TitleTest") ;;
  04) tasks=(:core:jvmTest --tests "lab.taskmanager.TaskTest" --tests "lab.taskmanager.DomainErrorTest"
             :lessons:test --tests "lab.exercises.Exercise4*") ;;
  05) tasks=(:core:jvmTest --tests "lab.taskmanager.OutcomeTest"
             :lessons:test --tests "lab.exercises.Exercise5*") ;;
  06) tasks=(:core:jvmTest --tests "lab.taskmanager.TaskExtensionsTest"
             :lessons:test --tests "lab.exercises.Exercise6*") ;;
  07) tasks=(:core:jvmTest --tests "lab.taskmanager.UseCaseTest") ;;
  08) tasks=(:lessons:test --tests "lab.taskmanager.CliTest") ;;
  09) tasks=(:core:jvmTest --tests "lab.taskmanager.OutcomeGenericsTest" --tests "lab.taskmanager.LoggingTaskRepositoryTest"
             --tests "lab.taskmanager.TaskSeedTest") ;;
  10) tasks=(:lessons:test --tests "lab.exercises.Exercise7*") ;;
  11) tasks=(:core:jvmTest --tests "lab.taskmanager.ContractTest" --tests "lab.taskmanager.FileTaskRepositoryTest") ;;
  12) tasks=(:backend:test) ;;
  13) tasks=(:core:jvmTest --tests "lab.taskmanager.PlatformTest" :core:compileKotlinWasmJs :core:compileDebugKotlinAndroid)
      $darwin && tasks+=(:core:compileKotlinIosSimulatorArm64) ;;
  14) tasks=(:composeApp:desktopTest) ;;
  15) tasks=(:composeApp:compileKotlinDesktop :composeApp:compileKotlinWasmJs :composeApp:assembleDebug)
      $darwin && tasks+=(:composeApp:linkDebugFrameworkIosSimulatorArm64) ;;
  16) tasks=(:core:jvmTest :lessons:test :backend:test :composeApp:desktopTest) ;;
  *) echo "módulo desconhecido: $module (use 00 a 16)" >&2; exit 2 ;;
esac

echo "▶ Módulo $module: rodando testes"
"$G" "${tasks[@]}" --console=plain

# Gate extra: o módulo só vale se ninguém deixou o TODO("MNN...") no lugar. Módulo 16 exige ZERO TODOs no projeto.
if [ "$module" = "16" ]; then pattern='TODO\("M[0-9][0-9]'; else pattern="TODO\\(\"M$module"; fi
if grep -rnE "$pattern" --include='*.kt' core lessons backend composeApp 2>/dev/null; then
  echo "✘ Módulo $module: ainda há TODO(\"M…\") nos arquivos acima. Implemente-os." >&2
  exit 1
fi

echo "✔ Módulo $module concluído."
