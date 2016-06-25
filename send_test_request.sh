#!/usr/bin/env bash
curl --header "content-type: text/xml" -d src/test/resources/request.xml  http://localhost:8080/ws
