const path = require('path');

module.exports = {
    entry: './src/main/resources/static/js/templates.js',
    output: {
        path: path.resolve(__dirname, 'out/production/static/js'),
        filename: 'bundle.js'
    }
};