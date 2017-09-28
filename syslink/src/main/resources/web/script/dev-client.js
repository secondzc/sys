/* eslint-disable */
require('eventsource-polyfill')
//var hotClient = require('webpack-hot-middleware/client?noInfo=true&reload=true')
var hotClient = require('webpack-hot-middleware/client?http://gogs.modelica-china.com:8080/')
hotClient.subscribe(function (event) {
  if (event.action === 'reload') {
    window.location.reload()
  }
})
