package com.netflix.contentservice.model;

/*
Tracks the video processing lifecyle
*
*
*FLOW:
*PENDING -> UPLOADED -> ENCODING -> ENCODED -> READY
                                 -> FAILED
 */

public enum VideoStatus {

    PENDING, // movie added but no uploaded yet
    UPLOADED, // raw video uploaded to S3
    ENCODING, //FFmpeg is encoding the video
    ENCODED, //Encoding complete
    READY, //HLS playList ready can be streamed
    FAILED // encoding failed
}
