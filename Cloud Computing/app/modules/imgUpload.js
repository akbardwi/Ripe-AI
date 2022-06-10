'use strict'
const {Storage} = require('@google-cloud/storage')
const fs = require('fs')
const dateFormat = require('dateformat')
const path = require('path');
const { v4: uuidv4 } = require('uuid');

const pathKey = path.resolve('./ripe-ai-88a1ecfabb5f.json')

// TODO: Sesuaikan konfigurasi Storage
const gcs = new Storage({
    projectId: 'ripe-ai',
    keyFilename: pathKey
})

// TODO: Tambahkan nama bucket yang digunakan
const bucketName = 'ripe-ai-bucket'
const bucket = gcs.bucket(bucketName)

function getPublicUrl(filename) {
    return 'https://storage.googleapis.com/' + bucketName + '/' + filename;
}

let ImgUpload = {}

ImgUpload.uploadToGcs = (req, res, next) => {
    if (!req.file && !req.body.fruit) return next()
    const filename = req.file.originalname
    const extension = path.extname(filename)
    const gcsname = req.body.fruit + '/' + uuidv4() + extension
    const file = bucket.file(gcsname)

    const stream = file.createWriteStream({
        metadata: {
            contentType: req.file.mimetype
        }
    })

    stream.on('error', (err) => {
        req.file.cloudStorageError = err
        next(err)
    })

    stream.on('finish', () => {
        req.file.cloudStorageObject = gcsname
        req.file.cloudStoragePublicUrl = getPublicUrl(gcsname)
        next()
    })

    stream.end(req.file.buffer)
}

module.exports = ImgUpload