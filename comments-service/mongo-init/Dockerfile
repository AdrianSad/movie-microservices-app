FROM mongo

COPY init.json /init.json
CMD mongoimport --host mongo --db comments-db --collection comments --drop --type json --file /init.json --jsonArray