#!/bin/bash
set -e
FILE_NAME="$(basename $0)"

run(){
  echo "running1"
  print "RUNNING : ${1}"
  $1
}

ROOT="$(git rev-parse --show-toplevel)"
cd $ROOT

mvn clean install