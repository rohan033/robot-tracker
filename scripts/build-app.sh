#!/bin/bash
set -e
FILE_NAME="$(basename $0)"

ROOT="$(git rev-parse --show-toplevel)"
cd $ROOT

mvn clean install