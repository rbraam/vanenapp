module.exports = function(grunt) {

	// Project configuration.
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		jshint:{
			all: ["Gruntfile.js",
				"src/main/webapp/js/*.js"],
			options: {jshintrc: '.jshintrc'}
		}
	});

	// Load the plugin that provides the "uglify" task.
	grunt.loadNpmTasks('grunt-contrib-jshint');

	//test
	grunt.registerTask('test', ['jshint']);
	// Default task(s).
	grunt.registerTask('default', ['test']);
};