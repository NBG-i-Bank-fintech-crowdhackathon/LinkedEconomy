/**
 * INSPINIA - Responsive Admin Theme
 *
 * Main controller.js file
 * Define controllers with data used in Inspinia theme
 *
 *
 * Functions (controllers)
 *  - MainCtrl
 *  - dashboardFlotOne
 *  - dashboardFlotTwo
 *  - dashboardFlotFive
 *  - dashboardMap
 *  - flotChartCtrl
 *  - rickshawChartCtrl
 *  - sparklineChartCtrl
 *  - widgetFlotChart
 *  - modalDemoCtrl
 *  - ionSlider
 *  - wizardCtrl
 *  - CalendarCtrl
 *  - chartJsCtrl
 *  - GoogleMaps
 *  - ngGridCtrl
 *  - codeEditorCtrl
 *  - nestableCtrl
 *  - notifyCtrl
 *  - translateCtrl
 *  - imageCrop
 *  - diff
 *  - idleTimer
 *  - liveFavicon
 *  - formValidation
 *  - agileBoard
 *  - draggablePanels
 *  - chartistCtrl
 *  - metricsCtrl
 *  - sweetAlertCtrl
 *  - selectCtrl
 *  - toastrCtrl
 *  - loadingCtrl
 *  - datatablesCtrl
 *  - truncateCtrl
 *  - touchspinCtrl
 *  - tourCtrl
 *  - jstreeCtrl
 *
 *
 */

/**
 * MainCtrl - controller
 * Contains severals global data used in diferent view
 *
 */
function MainCtrl() {

    /**
     * daterange - Used as initial model for data range picker in Advanced form view
     */
    this.daterange = {startDate: null, endDate: null}

    /**
     * slideInterval - Interval for bootstrap Carousel, in milliseconds:
     */
    this.slideInterval = 5000;


    /**
     * states - Data used in Advanced Form view for Chosen plugin
     */
    this.states = [
        'Alabama',
        'Alaska',
        'Arizona',
        'Arkansas',
        'California',
        'Colorado',
        'Connecticut',
        'Delaware',
        'Florida',
        'Georgia',
        'Hawaii',
        'Idaho',
        'Illinois',
        'Indiana',
        'Iowa',
        'Kansas',
        'Kentucky',
        'Louisiana',
        'Maine',
        'Maryland',
        'Massachusetts',
        'Michigan',
        'Minnesota',
        'Mississippi',
        'Missouri',
        'Montana',
        'Nebraska',
        'Nevada',
        'New Hampshire',
        'New Jersey',
        'New Mexico',
        'New York',
        'North Carolina',
        'North Dakota',
        'Ohio',
        'Oklahoma',
        'Oregon',
        'Pennsylvania',
        'Rhode Island',
        'South Carolina',
        'South Dakota',
        'Tennessee',
        'Texas',
        'Utah',
        'Vermont',
        'Virginia',
        'Washington',
        'West Virginia',
        'Wisconsin',
        'Wyoming'
    ];

    /**
     * check's - Few variables for checkbox input used in iCheck plugin. Only for demo purpose
     */
    this.checkOne = true;
    this.checkTwo = true;
    this.checkThree = true;
    this.checkFour = true;

    /**
     * knobs - Few variables for knob plugin used in Advanced Plugins view
     */
    this.knobOne = 75;
    this.knobTwo = 25;
    this.knobThree = 50;

    /**
     * Variables used for Ui Elements view
     */
    this.bigTotalItems = 175;
    this.bigCurrentPage = 1;
    this.maxSize = 5;
    this.singleModel = 1;
    this.radioModel = 'Middle';
    this.checkModel = {
        left: false,
        middle: true,
        right: false
    };

    /**
     * groups - used for Collapse panels in Tabs and Panels view
     */
    this.groups = [
        {
            title: 'Dynamic Group Header - 1',
            content: 'Dynamic Group Body - 1'
        },
        {
            title: 'Dynamic Group Header - 2',
            content: 'Dynamic Group Body - 2'
        }
    ];

    /**
     * alerts - used for dynamic alerts in Notifications and Tooltips view
     */
    this.alerts = [
        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
        { type: 'success', msg: 'Well done! You successfully read this important alert message.' },
        { type: 'info', msg: 'OK, You are done a great job man.' }
    ];

    /**
     * addAlert, closeAlert  - used to manage alerts in Notifications and Tooltips view
     */
    this.addAlert = function() {
        this.alerts.push({msg: 'Another alert!'});
    };

    this.closeAlert = function(index) {
        this.alerts.splice(index, 1);
    };

    /**
     * randomStacked - used for progress bar (stacked type) in Badges adn Labels view
     */
    this.randomStacked = function() {
        this.stacked = [];
        var types = ['success', 'info', 'warning', 'danger'];

        for (var i = 0, n = Math.floor((Math.random() * 4) + 1); i < n; i++) {
            var index = Math.floor((Math.random() * 4));
            this.stacked.push({
                value: Math.floor((Math.random() * 30) + 1),
                type: types[index]
            });
        }
    };
    /**
     * initial run for random stacked value
     */
    this.randomStacked();

    /**
     * summernoteText - used for Summernote plugin
     */
    this.summernoteText = ['<h3>Hello Jonathan! </h3>',
    '<p>dummy text of the printing and typesetting industry. <strong>Lorem Ipsum has been the dustrys</strong> standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more',
        'recently with</p>'].join('');

    /**
     * General variables for Peity Charts
     * used in many view so this is in Main controller
     */
    this.BarChart = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2, 4, 7, 3, 2, 7, 9, 6, 4, 5, 7, 3, 2, 1, 0, 9, 5, 6, 8, 3, 2, 1],
        options: {
            fill: ["#1ab394", "#d7d7d7"],
            width: 100
        }
    };

    this.BarChart2 = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.BarChart3 = {
        data: [5, 3, 2, -1, -3, -2, 2, 3, 5, 2],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.LineChart = {
        data: [5, 9, 7, 3, 5, 2, 5, 3, 9, 6, 5, 9, 4, 7, 3, 2, 9, 8, 7, 4, 5, 1, 2, 9, 5, 4, 7],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart2 = {
        data: [3, 2, 9, 8, 47, 4, 5, 1, 2, 9, 5, 4, 7],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart3 = {
        data: [5, 3, 2, -1, -3, -2, 2, 3, 5, 2],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart4 = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.PieChart = {
        data: [1, 5],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.PieChart2 = {
        data: [226, 360],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart3 = {
        data: [0.52, 1.561],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart4 = {
        data: [1, 4],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart5 = {
        data: [226, 134],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart6 = {
        data: [0.52, 1.041],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
};



 




/**
 * flotChartCtrl - Controller for data for All flot chart
 * used in Flot chart view
 */

function flotChartCtrl() {

    

    /**
     * Pie Chart Data
     */
	 
	
	
	var pieData = [
        {
            label: "OTE",
            data: 1243478,
            color: "#008000"
        },
        {
            label: "VOFADONE",
            data: 76800,
            color: "#ff0000"
        },
        {
            label: "FORTHNET",
            data: 358043,
            color: "#ffa500"
        },
        {
            label: "CYTA",
            data: 518087,
            color: "#ffff00"
        },
		{
            label: "WIND",
            data: 8306881,
            color: "#0000ff"
        }
    ];
	
	

    /**
     * Pie Chart Options
     */
    var pieOptions = {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        }
    };

   


    /**
     * Definition of variables
     * Flot chart
     */

    this.flotPieData = pieData;
    this.flotPieOptions = pieOptions;

}






/**
 * wizardCtrl - Controller for wizard functions
 * used in Wizard view
 */
function wizardCtrl($scope, $rootScope) {
    // All data will be store in this object
    $scope.formData = {};

    // After process wizard
    $scope.processForm = function() {
        alert('Wizard completed');
    };

}


/**
 * CalendarCtrl - Controller for Calendar
 * Store data events for calendar
 */
function CalendarCtrl($scope) {

    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();

    // Events
    $scope.events = [
        {title: 'All Day Event',start: new Date(y, m, 1)},
        {title: 'Long Event',start: new Date(y, m, d - 5),end: new Date(y, m, d - 2)},
        {id: 999,title: 'Repeating Event',start: new Date(y, m, d - 3, 16, 0),allDay: false},
        {id: 999,title: 'Repeating Event',start: new Date(y, m, d + 4, 16, 0),allDay: false},
        {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false},
        {title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
    ];


    /* message on eventClick */
    $scope.alertOnEventClick = function( event, allDay, jsEvent, view ){
        $scope.alertMessage = (event.title + ': Clicked ');
    };
    /* message on Drop */
    $scope.alertOnDrop = function(event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view){
        $scope.alertMessage = (event.title +': Droped to make dayDelta ' + dayDelta);
    };
    /* message on Resize */
    $scope.alertOnResize = function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view ){
        $scope.alertMessage = (event.title +': Resized to make dayDelta ' + minuteDelta);
    };

    /* config object */
    $scope.uiConfig = {
        calendar:{
            height: 450,
            editable: true,
            header: {
                left: 'prev,next',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            eventClick: $scope.alertOnEventClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize
        }
    };

    /* Event sources array */
    $scope.eventSources = [$scope.events];
}

/**
 * chartJsCtrl - Controller for data for ChartJs plugin
 * used in Chart.js view
 */
 function chartJs2Ctrl($scope, $http) {
	 
	
	$scope.dashboard4 = {};
	
//	$http.get('json/payments_ote_graph.json')

	$http.get('json/OTE/OTE_Group_yearly_payments.json')


       .then(function(res){
			
			
			$scope.dashboard4.labels = res.data.labels;
			$scope.dashboard4.datasets = res.data.datasets;
			
	});
	
	
	this.lineData = $scope.dashboard4;
	
    /**
     * Data for Line chart
     */



    /**
     * Options for Line chart
     */
    this.lineOptions = {
        scaleShowGridLines : true,
        scaleGridLineColor : "rgba(0,0,0,.05)",
        scaleGridLineWidth : 1,
        bezierCurve : true,
        bezierCurveTension : 0.4,
        pointDot : true,
        pointDotRadius : 4,
        pointDotStrokeWidth : 1,
        pointHitDetectionRadius : 20,
        datasetStroke : true,
        datasetStrokeWidth : 2,
        datasetFill : true
    };
	
	
 
};



function chartJs3Ctrl($scope, $http) {
	 
	
	$scope.dashboard4 = {};

	$http.get('json/OTE/Social_Impact.json')


       .then(function(res){
			
			
			$scope.dashboard4.labels = res.data.labels;
			$scope.dashboard4.datasets = res.data.datasets;
			
	});
	
	
	this.lineData = $scope.dashboard4;
	
    /**
     * Data for Line chart
     */



    /**
     * Options for Line chart
     */
    this.lineOptions = {
        scaleShowGridLines : true,
        scaleGridLineColor : "rgba(0,0,0,.05)",
        scaleGridLineWidth : 1,
        bezierCurve : true,
        bezierCurveTension : 0.4,
        pointDot : true,
        pointDotRadius : 4,
        pointDotStrokeWidth : 1,
        pointHitDetectionRadius : 20,
        datasetStroke : true,
        datasetStrokeWidth : 2,
        datasetFill : false
    };
	
	
 
};
	 
 
 



/**
 * notifyCtrl - Controller angular notify
 */
function notifyCtrl($scope, notify) {
    $scope.msg = 'Hello! This is a sample message!';
    $scope.demo = function () {
        notify({
            message: $scope.msg,
            classes: $scope.classes,
            templateUrl: $scope.template
        });
    };
    $scope.closeAll = function () {
        notify.closeAll();
    };

    $scope.inspiniaTemplate = 'views/common/notify.html';
    $scope.inspiniaDemo1 = function(){
        notify({ message: 'Info - This is a Inspinia info notification', classes: 'alert-info', templateUrl: $scope.inspiniaTemplate});
    }
    $scope.inspiniaDemo2 = function(){
        notify({ message: 'Success - This is a Inspinia success notification', classes: 'alert-success', templateUrl: $scope.inspiniaTemplate});
    }
    $scope.inspiniaDemo3 = function(){
        notify({ message: 'Warning - This is a Inspinia warning notification', classes: 'alert-warning', templateUrl: $scope.inspiniaTemplate});
    }
    $scope.inspiniaDemo4 = function(){
        notify({ message: 'Danger - This is a Inspinia danger notification', classes: 'alert-danger', templateUrl: $scope.inspiniaTemplate});
    }
}

/**
 * translateCtrl - Controller for translate
 */
function translateCtrl($translate, $scope) {
    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
        $scope.language = langKey;
    };
}





/**
 * metricsCtrl - Controller for data for all Sparkline chart
 * used in Metrics view
 */
function metricsCtrl() {


	this.demo1Options = {
        type: 'line',
        width: '100%',
        height: '60',
        lineColor: '#1ab394',
        fillColor: "#ffffff"
    };

    

}




function paymentsCtrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Payments'},
            {extend: 'pdf', title: 'Payments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/datatables/OTEPayments.json')
       .then(function(res){
			$scope.payments = res.data.DataForTableOTEPaymentsDtls;
        });	
}

function assignmentsCtrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Assignments'},
            {extend: 'pdf', title: 'Assignments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/datatables/OTEAssignments.json')
       .then(function(res){
			$scope.assignments = res.data.DataForTableOTEAssignmentsDtls;
        });	
}

function subsidiesCtrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Subsidies'},
            {extend: 'pdf', title: 'Subsidies'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/datatables/OTEESPA.json')
       .then(function(res){
			$scope.subsidies = res.data.DataForTableOTEESPADtls;
        });	
}




function payments2Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Payments'},
            {extend: 'pdf', title: 'Payments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
//		$http.get('json/new_json3/OTE_Group_payments.json')
		$http.get('json/OTE/OTE_Group_payments.json')
       .then(function(res){
			$scope.payments = res.data.decisions;
        });	
}

function assignments2Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Assignments'},
            {extend: 'pdf', title: 'Assignments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/OTE/OTE_Group_assignments.json')
       .then(function(res){
			$scope.assignments = res.data.decisions;
        });	
}

function subsidies2Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Subsidies'},
            {extend: 'pdf', title: 'Subsidies'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/OTE/OTE_Group_espa.json')
       .then(function(res){
			$scope.subsidies = res.data.decisions;
        });	
}



function payments_ote_eu_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Payments'},
            {extend: 'pdf', title: 'Payments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		

		$http.get('json/OTE/OTE_Group_EuFts.json')
       .then(function(res){
			$scope.payments = res.data.decisions;
        });	
}


/**
 * chartistCtrl - Controller for Chartist library
 */
function chartistCtrl() {

   

    this.horizontalData = {"labels": ["Influence Metric"], "series": [[16.21],[21.81],[12.54],[9.25],[11.43]]}
	

    this.horizontalOptions = {
        seriesBarDistance: 15,
        reverseData: true,
        horizontalBars: true,
        axisY: {
            offset: 50
        }
    }

    

}

/**
 * chartistCtrl - Controller for Chartist library
 */
function chartistCtrl2() {


	this.horizontalData = {"labels": ["Followers"], "series": [[27120],[12791],[4657],[1557],[9293]]}

    this.horizontalOptions = {
        seriesBarDistance: 15,
        reverseData: true,
        horizontalBars: true,
        axisY: {
            offset: 50
        }
    }

    

}


/**
 * chartistCtrl - Controller for Chartist library
 */
function chartistCtrl3($scope, $http) {
	

/*

	this.horizontalData = {
        labels: ['Followers'],
        series: [
            [15001], 
			[11543], 
			[21076], 
			[9657], 
			[3456]
           
        ]
    }

*/	
	
    this.horizontalOptions = {
        seriesBarDistance: 15,
        reverseData: false,
        horizontalBars: true,
        axisY: {
            offset: 50
        }
    }
	

}


function fekCtrl($scope, $http){

    
		
		$http.get('json/fek/fek2.json')
       .then(function(res){
			$scope.fek_decisions = res.data.FekDtls;
        });	
}


function payments_oracle_diavgeia_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Payments'},
            {extend: 'pdf', title: 'Payments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		

		$http.get('json/ORACLE/datatables/Oracle_Diavgeia_payments.json')
       .then(function(res){
			$scope.payments = res.data.decisions;
        });	
}



function payments_oracle_australia_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Assignments'},
            {extend: 'pdf', title: 'Assignments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/ORACLE/datatables/Oracle_Australia_payments.json')
       .then(function(res){
			$scope.payments = res.data.payments;
        });	
}


function payments_oracle_europe_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Assignments'},
            {extend: 'pdf', title: 'Assignments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/ORACLE/datatables/Oracle_Eufts_payments.json')
       .then(function(res){
			$scope.payments = res.data.payments;
        });	
}



function payments_nbg_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Payments'},
            {extend: 'pdf', title: 'Payments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		

		$http.get('json/NBG/ETHNIKI_Group_payments.json')
       .then(function(res){
			$scope.payments = res.data.decisions;
        });	
}

function assignments_nbg_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Assignments'},
            {extend: 'pdf', title: 'Assignments'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/NBG/ETHNIKI_Group_assignments.json')
       .then(function(res){
			$scope.assignments = res.data.decisions;
        });	
}

function subsidies_nbg_Ctrl($scope, $http, DTOptionsBuilder, DTColumnDefBuilder){

    $scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'Subsidies'},
            {extend: 'pdf', title: 'Subsidies'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);
		
		$http.get('json/NBG/Ethniki_Group_espa.json')
       .then(function(res){
			$scope.subsidies = res.data.decisions;
        });	
}


 function chartJs_nbg_yearly_payments_Ctrl($scope, $http) {
	 
	
	$scope.dashboard4 = {};
	


	$http.get('json/NBG/Ethniki_Group_yearly_payments.json')


       .then(function(res){
			
			
			$scope.dashboard4.labels = res.data.labels;
			$scope.dashboard4.datasets = res.data.datasets;
			
	});
	
	
	this.lineData = $scope.dashboard4;
	
    /**
     * Data for Line chart
     */



    /**
     * Options for Line chart
     */
    this.lineOptions = {
        scaleShowGridLines : true,
        scaleGridLineColor : "rgba(0,0,0,.05)",
        scaleGridLineWidth : 1,
        bezierCurve : true,
        bezierCurveTension : 0.4,
        pointDot : true,
        pointDotRadius : 4,
        pointDotStrokeWidth : 1,
        pointHitDetectionRadius : 20,
        datasetStroke : true,
        datasetStrokeWidth : 2,
        datasetFill : true
    };
	
	
 
};



function chartJs_nbg_social_impact_Ctrl($scope, $http) {
	 
	
	$scope.dashboard4 = {};

	$http.get('json/NBG/Social_Impact.json')


       .then(function(res){
			
			
			$scope.dashboard4.labels = res.data.labels;
			$scope.dashboard4.datasets = res.data.datasets;
			
	});
	
	
	this.lineData = $scope.dashboard4;
	
    /**
     * Data for Line chart
     */



    /**
     * Options for Line chart
     */
    this.lineOptions = {
        scaleShowGridLines : true,
        scaleGridLineColor : "rgba(0,0,0,.05)",
        scaleGridLineWidth : 1,
        bezierCurve : true,
        bezierCurveTension : 0.4,
        pointDot : true,
        pointDotRadius : 4,
        pointDotStrokeWidth : 1,
        pointHitDetectionRadius : 20,
        datasetStroke : true,
        datasetStrokeWidth : 2,
        datasetFill : false
    };
	
	
 
};


function chartistCtrl_nbg() {

   

    this.horizontalData = {"labels": ["Influence Metric"], "series": [[2.03],[13.99],[21.12],[15.73]]}
	

    this.horizontalOptions = {
        seriesBarDistance: 15,
        reverseData: true,
        horizontalBars: true,
        axisY: {
            offset: 50
        }
    }

    

}

/**
 * chartistCtrl - Controller for Chartist library
 */
function chartistCtrl2_nbg() {


	this.horizontalData = {"labels": ["Followers"], "series": [[960],[2962],[2852],[1593]]}

    this.horizontalOptions = {
        seriesBarDistance: 15,
        reverseData: true,
        horizontalBars: true,
        axisY: {
            offset: 50
        }
    }

    

}



function linked_financial_ratios($scope, $http){

    
		
		$http.get('json/OTE/linked_financial_ratios.json')
       .then(function(res){
			$scope.decisions = res.data.details;
        });	
}


/**
 *
 * Pass all functions into module
 */
angular
    .module('inspinia')
    .controller('MainCtrl', MainCtrl)
//	  .controller('dashboardFlotOne', dashboardFlotOne)
//    .controller('dashboardFlotTwo', dashboardFlotTwo)
//    .controller('dashboardFive', dashboardFive)
//    .controller('dashboardMap', dashboardMap)
    .controller('flotChartCtrl', flotChartCtrl)
//    .controller('rickshawChartCtrl', rickshawChartCtrl)
//    .controller('sparklineChartCtrl', sparklineChartCtrl)
//    .controller('widgetFlotChart', widgetFlotChart)
//    .controller('modalDemoCtrl', modalDemoCtrl)
//    .controller('ionSlider', ionSlider)
//    .controller('wizardCtrl', wizardCtrl)
//    .controller('CalendarCtrl', CalendarCtrl)
//    .controller('chartJsCtrl', chartJsCtrl)
//    .controller('GoogleMaps', GoogleMaps)
//    .controller('ngGridCtrl', ngGridCtrl)
//    .controller('codeEditorCtrl', codeEditorCtrl)
//    .controller('nestableCtrl', nestableCtrl)
//    .controller('notifyCtrl', notifyCtrl)
//    .controller('translateCtrl', translateCtrl)
//    .controller('imageCrop', imageCrop)
//    .controller('diff', diff)
//    .controller('idleTimer', idleTimer)
//    .controller('liveFavicon', liveFavicon)
//    .controller('formValidation', formValidation)
//    .controller('agileBoard', agileBoard)
//    .controller('draggablePanels', draggablePanels)
    .controller('chartistCtrl', chartistCtrl)
	.controller('chartistCtrl2', chartistCtrl2)
	.controller('chartistCtrl3', chartistCtrl3)
    .controller('metricsCtrl', metricsCtrl)
//    .controller('sweetAlertCtrl', sweetAlertCtrl)
//    .controller('selectCtrl', selectCtrl)
//    .controller('toastrCtrl', toastrCtrl)
//    .controller('loadingCtrl', loadingCtrl)
//   .controller('datatablesCtrl', datatablesCtrl)
//    .controller('truncateCtrl', truncateCtrl)
//    .controller('touchspinCtrl', touchspinCtrl)
//    .controller('tourCtrl', tourCtrl)
//    .controller('jstreeCtrl', jstreeCtrl)
	.controller('fekCtrl', fekCtrl)
	.controller('paymentsCtrl', paymentsCtrl)
	.controller('assignmentsCtrl', assignmentsCtrl)
	.controller('subsidiesCtrl', subsidiesCtrl)
	.controller('chartJs2Ctrl', chartJs2Ctrl)
	.controller('chartJs3Ctrl', chartJs3Ctrl)
	.controller('payments2Ctrl', payments2Ctrl)
	.controller('assignments2Ctrl', assignments2Ctrl)
	.controller('subsidies2Ctrl', subsidies2Ctrl)
	.controller('payments_oracle_diavgeia_Ctrl', payments_oracle_diavgeia_Ctrl)
	.controller('payments_oracle_australia_Ctrl', payments_oracle_australia_Ctrl)
	.controller('payments_oracle_europe_Ctrl', payments_oracle_europe_Ctrl)
	.controller('payments_nbg_Ctrl', payments_nbg_Ctrl)
	.controller('assignments_nbg_Ctrl', assignments_nbg_Ctrl)
	.controller('subsidies_nbg_Ctrl', subsidies_nbg_Ctrl)
	.controller('chartJs_nbg_yearly_payments_Ctrl', chartJs_nbg_yearly_payments_Ctrl)
	.controller('chartJs_nbg_social_impact_Ctrl', chartJs_nbg_social_impact_Ctrl)
	.controller('chartistCtrl_nbg', chartistCtrl_nbg)
	.controller('chartistCtrl2_nbg', chartistCtrl2_nbg)
	.controller('payments_ote_eu_Ctrl', payments_ote_eu_Ctrl)
	.controller('linked_financial_ratios', linked_financial_ratios);
	
	
	
	
	
	
	
	
	

