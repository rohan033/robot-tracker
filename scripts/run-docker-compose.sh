#!/bin/bash

set -e
ROOT="$(git rev-parse --show-toplevel)"
cd $ROOT

docker-compose up