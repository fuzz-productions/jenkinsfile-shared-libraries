def call() {
	try {
		checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/*lint.xml', unHealthy: ''
		pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/*pmd.xml', unHealthy: ''
		dry canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/*cpd.xml', unHealthy: ''
		openTasks canComputeNew: false, defaultEncoding: '', excludePattern: '**/Libraries/**, **/Pods/**, **/*.framework/**, **/Xcode.app/**, **/build/**', healthy: '', high: 'FIXME,shit,fuck,suck', ignoreCase: true, low: 'deprecated', normal: 'TODO', pattern: '**/*.swift, **/*.java, **/*.m, **/*.h, **/*.c', unHealthy: ''
		sloccountPublish encoding: '', pattern: '**/*cloc.xml'
	} catch (Exception e) {
		//Silent Error
	}
}