# Maven Install

This directory consists of the script file that will do maven install for all the spring boot microservices at once and creates jar files for each. quit handy if you don't want to do maven install for each application manually.

## Bash Commands

### Maven Install

```bash
chmod +x run_mvn_install.sh

./run_mvn_install.sh
```

### Maven Start

```bash
chmod +x run_mvn_start.sh

./run_mvn_start.sh
```

**Note:** increase sleep time if facing issues with config server not starting before other services

**_Prerequisites:_** Maven git bash if running on windows
