const gulp = require('gulp');
const concat = require('gulp-concat');
const path = require('path');
const modulesPath = "./node_modules/";
const distPath = path.resolve(__dirname, 'src/main/webapp/static/dist');
const jsPath = path.resolve(distPath, 'js');
const cssPath = path.resolve(distPath, 'css');
const fontPath = path.resolve(distPath, 'fonts');

const scripts = [
    modulesPath + "jquery/dist/jquery.min.js",
    modulesPath + "bootstrap/dist/js/bootstrap.min.js"
];

const styles = [
    modulesPath + "bootstrap/dist/css/bootstrap.min.css",
    modulesPath + "font-awesome/css/font-awesome.min.css"
];

const fonts = [
    modulesPath + "bootstrap/dist/fonts/*",
    modulesPath + "font-awesome/fonts/*"
];

gulp.task('bundle-js', function() {
    return gulp.src(scripts)
        .pipe(concat('bundle.js'))
        .pipe(gulp.dest(jsPath));
});

gulp.task('bundle-css', function() {
    return gulp.src(styles)
        .pipe(concat('bundle.css'))
        .pipe(gulp.dest(cssPath));
});

gulp.task('copy-fonts', function() {
    return gulp.src(fonts)
        .pipe(gulp.dest(fontPath));
});

gulp.task('default', ['bundle-js', 'bundle-css', 'copy-fonts']);