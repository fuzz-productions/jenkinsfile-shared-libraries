def call(Closure body) {
	stage("Test") {
		body()
		reportResultsAndCoverage
	}
}