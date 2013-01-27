#!/bin/bash

APP_HOME=$(cd `dirname $0` && pwd)
ARTIFACT=my-example-service


DROPWIZARD_SETTINGS="-Ddw.logging.console.enabled=false \
    -Ddw.logging.file.enabled=true \
    -Ddw.logging.syslog.enabled=false"

StartUp()
{
	cd ${APP_HOME}
	mkdir -p logs
	nohup ${JAVA_HOME}/bin/java ${DROPWIZARD_SETTINGS} -jar ${ARTIFACT}/target/${ARTIFACT}*SNAPSHOT.jar \
	    server ${ARTIFACT}/config-production.yml > logs/stdout.${ARTIFACT}.log 2>&1 </dev/null &
}

Migrate()
{
	cd ${APP_HOME}
	mkdir -p logs
	${JAVA_HOME}/bin/java ${DROPWIZARD_SETTINGS} -jar ${ARTIFACT}/target/${ARTIFACT}*SNAPSHOT.jar \
	    db migrate ${ARTIFACT}/config-production.yml
}

ShutDown()
{
	_pid=`${JAVA_HOME}/bin/jps -mv |grep ${ARTIFACT} | cut -d' ' -f1` > /dev/null 2>&1
	if [ -n "${_pid}" ]
	then
		echo "Trying to kill pid '${_pid}' found from jps"
		kill -s term ${_pid} > /dev/null 2>&1
		_retval=$?
		if [ ${_retval} -ne 0 ] ; then
			echo "Failed to send kill signal!"
			exit ${_retval}
		fi
	else
		echo "Could not find pid with jps! Use 'ps' and kill manually."
		exit 1
	fi
}

SCRIPT_ACTION=$1
case ${SCRIPT_ACTION} in
	start)
		StartUp
		_retval=$?
		;;
	stop)
		ShutDown
		_retval=$?
		;;
	migrate)
	    Migrate
	    _retval=$?
        ;;
	*)
		echo "Usage:"
		echo "$0 start|stop|migrate"
		;;
esac
