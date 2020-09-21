FROM mongo

COPY commentsInit.json /commentsInit.json
CMD mongoimport --host mongo --db comments-db --collection comments --drop --type json --file /commentsInit.json --jsonArray

COPY ratingsInit.json /ratingsInit.json
CMD mongoimport --host mongo --db ratings-db --collection ratings --drop --type json --file /ratingsInit.json --jsonArray