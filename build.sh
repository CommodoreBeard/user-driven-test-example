#!/usr/bin/env bash

set -e

IMAGE=${1:-"user-driven-test-example"}
VERSION=${2:-"latest"}
REPO=${3:-"local"}

docker build -t ${REPO}/${IMAGE}:${VERSION} "$(dirname $0)"

docker rmi -f $(docker images -f dangling=true -q) 2>/dev/null