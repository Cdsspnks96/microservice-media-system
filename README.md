# ENG2-media-system
## Overview
This is a simple media service designed for the purpose of the MEng Engineering2 module. 
Users can post videos and like/dislike them, and these events will drive trending hashtags and subscription lists.

## Prerequisites for use
Before you begin, ensure you have the following installed on your machine:

- Docker Desktop
- Bash shell

## Setup
Follow the steps below to set up and run the project.

### 1. Open a Bash Shell
First, open a Bash shell on your machine.

### 2. Change Directory
Navigate to the project's root directory by using the `cd` command.

### 3. Add CLI to your PATH
To interact with the microservices, add the CLI to your PATH:
```bash
PATH=$PATH:client/microservice-cli-0.1/bin
```
### 4. Start Docker Containers
Start the required Docker containers by running the provided shell script:
```bash
./scripts/compose-prod-up.sh
```
### 5. Create Kafka Topics
Once the containers are up, create the necessary Kafka topics by running:
```bash
./scripts/compose-kafka-topics-prod.sh
```
After these scripts have completed, the system will be up and ready to use.

## Usage
You can interact with the system via the command line. To view the full list of available commands, run:
```bash
microservice-cli -help
```
