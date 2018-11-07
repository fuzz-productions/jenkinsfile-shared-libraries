def call(String command) {
    slack.qsh "fastlane ${command}"
}

def clean(Closure body = null) {
	stage("Clean") {
		if (body != null) {
			body()
		}
		fastlane 'clean' 
	}
}

def install_certs(Closure body = null) {
	stage("Provision") {
		if (body != null) {
			body()
		}
		fastlane 'install_certs' 
	}
}

def install_dependencies(Closure body = null) {
	stage("Dependencies") {
		if (body != null) {
			body()
		}
		fastlane 'install_dependencies' 
	}
}

def run_reports(Closure body = null) {
	reportStage {
		sh 'fastlane run_reports'
		if (body != null) {
			body()
		}
	}
}

def setup(Closure body = null) {
	if (body != null) {
		body()
	}
	clean()
	install_certs()
	install_dependencies()
}

def perform_test(String inKeys = "", Closure body = null) {
	def keys = inKeys.split(",")
	if (body != null) {
		body()
	}
	for(key in keys){
		fastlane "perform_tests key:${key}"
	}
}

def performTestStage(String inKeys = "", Closure body = null) {
	testStage {
		if (body != null) {
			body()
		}
		perform_test(inKeys)
		archiveAppForTesting()
	}
}

def deploy_jenkins(String inKeys = "", Boolean resign = false, Closure body = null) {
	def keys = inKeys.split(",")
	if (body != null) {
		body()
	}
	for(key in keys){
		if (resign) {
			fastlane "deploy_jenkins key:${key} resign:true"
		} else {
			fastlane "deploy_jenkins key:${key}"
		}
	}
}

def deployStage(Map config, Closure body = null) {
	mobileBuildStage(config.get('name', '')) {
		if (body != null) {
			body()
		}
		if (!config.release) {
			deploy_jenkins(config.keys, config.get('resign', false)) 
		}
	}
}

def pipeline(Map config, Closure body) {
	setup()
	deployStage keys: config.keys, name: config.get('name', ''), resign: config.get('resign', false)
	performTestStage(config.keys)
	report()
}