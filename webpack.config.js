const path = require('path');

module.exports = {
    entry: './src/main/resources/static/js/templates.js',
    output: {
        path: path.resolve(__dirname, 'build/resources/main/static/js'),
        filename: 'bundle.js'
    }
};