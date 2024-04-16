#!/bin/bash

function check() {

  local exit_code=0
  pip3 install virtualenv

  virtualenv -p python3 sandbox
  source sandbox/bin/activate
  pip3 install -r requirements.txt

  mypy --python-version 3.7  --disallow-untyped-calls --disallow-untyped-defs --disallow-incomplete-defs \
     --disallow-any-unimported --disallow-any-expr --disallow-any-decorated \
     --disallow-any-explicit --disallow-any-generics \
     --warn-return-any --strict --show-error-context --show-column-numbers --no-incremental src/ test/ > errors.txt

  if [ -s errors.txt ]
  then
    awk 'BEGIN { print "\n"} { print $0} END { print "\n" NR " Type Errors found"}' < errors.txt
    exit_code=1
  else
    ls test/*.py | PYTHONPATH=./src:./test xargs python
    exit_code=$?
  fi

  deactivate

  return $exit_code
}

check
exit $?

#readonly exit_code=$?

#rm -rf sandbox
#rm -rf errors.txt
#rm -rf */__pycache__

#exit $exit_code
