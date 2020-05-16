// HTTP messages: https://www.w3schools.com/tags/ref_httpmessages.asp

const express = require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient
const url = "mongodb://localhost:27017"

app.use(express.json())


app.listen(3000, () => {
    console.log("Listening on port 3000...")
})

