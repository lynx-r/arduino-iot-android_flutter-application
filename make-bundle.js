const fs = require('fs');
const browserify = require('browserify');

browserify('./index.js', {
    standalone: 'iot'
})
  .transform({ global: true }, 'uglifyify')
  .bundle()
  .pipe(fs.createWriteStream('aws-iot-sdk.js'));
