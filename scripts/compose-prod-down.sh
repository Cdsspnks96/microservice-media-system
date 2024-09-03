#!/bin/bash

docker compose \
    -p microservice-prod \
    -f compose-prod.yml -f compose-prod-secrets.yml \
    stop