#!/usr/bin/env bash
set -euo pipefail

script_dir="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
cd "$script_dir"

rm -rf tests/build
mkdir -p tests/build
javac --release 11 -d tests/build tests/src/*.java
jar --create --file test.jar --manifest tests/manifest.mf -C tests/build .
rm -rf tests/build
echo "Built $script_dir/test.jar"

