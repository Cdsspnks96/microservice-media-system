#!/bin/bash

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic video-posted --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic video-viewed --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic video-tagged --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic video-liked --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic video-disliked --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic user-created --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic hashtag-created --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic tag-subbed --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic tag-unsubbed --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic videos-viewed-by-day --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic tags-subbed-by-day --replication-factor 3 --partitions 6

docker compose \
    -p microservice-prod \
    -f compose-prod.yml \
    exec -e JMX_PORT= kafka-0 kafka-topics.sh \
        --bootstrap-server kafka-0:9092 \
        --create --topic tags-liked-by-hour --replication-factor 3 --partitions 6