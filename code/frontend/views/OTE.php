<!doctype html>
<html>
<head>


<style>

@keyframes ticker {
	0%   {margin-left: 0}
	90%  {margin-left: -3150px}
	100%   {margin-left: 0}
}
@keyframes ticker2 {
	0%   {margin-left: 450px}
	100%  {margin-left: -350150px}
}

body { background: #333; width: 100%; height: 100% }

.news {
  box-shadow: inset 0 -15px 30px rgba(0,0,0,0.4), 0 5px 10px rgba(0,0,0,0.5);
  width: 1600px;
  min-height: 25px;
  margin: 20px auto;
  overflow: hidden;
  border-radius: 4px;
  padding: 3px;
  -webkit-user-select: none
} 

.news span {
  float: left;
  color: #fff;
  padding: 6px;
  position: relative;
  top: 1%;
  border-radius: 4px;
  box-shadow: inset 0 -15px 30px rgba(0,0,0,0.4);
  font: 16px 'Raleway', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -webkit-user-select: none;
  cursor: pointer
}

.news ul {
  float: left;
  width: 3500px;
  padding-left: 20px;
  animation: ticker linear 20s infinite;
  -webkit-user-select: none
}
.news.en1000 ul{
	width: 350350px;	
	animation: ticker2 linear 3000s infinite;
}

.news ul li {
	line-height: 25px; 
	list-style: none;
	float: left;
}

.news ul li div{
	width:350px;
}

.news ul li a {
  color: #fff;
  text-decoration: none;
  font: 14px 'Raleway',Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -webkit-user-select: none
}

.news ul:hover { animation-play-state: paused }
.news span:hover+ul { animation-play-state: paused }

/* OTHER COLORS */
.blue { background: #347fd0 }
.blue span { background: #2c66be }
.red { background: #d23435 }
.red span { background: #c22b2c }
.green { background: #699B67 }
.green span { background: #547d52 }
.magenta { background: #b63ace }
.magenta span { background: #842696 }
.custom { background: #808080 }
</style>
</head>

<body>



<?php

//metrics
$ch = curl_init("http://83.212.86.156:83/develop/json/metrics_json.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json = json_decode($content, true);
//end metrics


//metrics_usage
$ch = curl_init("http://83.212.86.156:83/develop/json/new_json3/metrics_cpv.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json2 = json_decode($content, true);
//end metrics_usage

//ote_group_dtls
$ch = curl_init("http://83.212.86.156:83/develop/json/OTE/OTE_Group_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json3 = json_decode($content, true);
//end ote_group_dtls

//ote_subsidiaries
$ch = curl_init("http://83.212.86.156:83/develop/json/OTE/OTE_Group_subsidiaries.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json4 = json_decode($content, true);
//end ote_subsidiaries


?>

<div class="wrapper wrapper-content">
<div class="row">


<div class="col-lg-3">
<h1>OTE Group</h1>
</div>

<div class="col-lg-3">
<img style="opacity:0.2;filter:alpha(opacity=20); width:230px;" src="logo/cosmote_logo_gr.png" alt="cosmote" >
</div>

</div>
</div>



<div class="wrapper wrapper-content">
<div class="row">

<div class="col-lg-6" ng-controller="linked_financial_ratios">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Linked financial ratios</h5>
                <div ibox-tools></div>
            </div>
            <div class="ibox-content">
                <table class="table table-hover no-margins">
                    <thead>
                    <tr>
					    <th>Εταιρεία</th>
                        <th>Πληρωτές</th>
						<th>Υπηρεσίες</th>
                        <th>Περιοχές (ΤΚ)</th>
                        <th>Αναμονή Πληρωμής (min)</th>
                        <th>Αναμονή Πληρωμής (max)</th>
                        <th>Αναμονή Πληρωμής (avg)</th>
                        
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="decision in decisions">
						<td>{{ decision.company }}</td>
						<td>{{ decision.buyers }}</td>
						<td>{{ decision.services }}</td>
						<td>{{ decision.regions }}</td>
						<td>{{ decision.min }}</td>
						<td>{{ decision.max }}</td>
						<td>{{ decision.avg }}</td>
					</tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>

	
	
	<div class="col-lg-6" ng-controller="fekCtrl">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Εταιρικές ανακοινώσεις στο ΦΕΚ</h5>
                <div ibox-tools></div>
            </div>
            <div class="ibox-content">
                <table class="table table-hover no-margins">
                    <thead>
                    <tr>
                        <th>Δημοσίευση</th>
                        <th>Εταιρεία</th>
                        <th>Θέμα</th>
                        <th>Πηγή</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="fek_decision in fek_decisions">
						<td>{{ fek_decision.fekPublDate }}</td>
						<td>{{ fek_decision.company }}</td>
						<td>{{ fek_decision.fekSubject }}</td>
						<td><a href="{{fek_decision.fekUrl}}" target=\"_blank\">{{ fek_decision.fekNumber }}</a></td>
					</tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>
	
	

</div>
</div> 
  	





<div class="wrapper wrapper-content">
<div class="row">

    <div class="col-lg-3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <span class="label label-success pull-right"><?php echo $json3['paymentDtls']['year']; ?></span>
                <h5>Πληρωμές Δημοσίου (ΔΙΑΥΓΕΙΑ)</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	//			echo "€ ".$english_format_number = number_format($responseArrayPayments["results"]["bindings"][0]["totalAmount"]["value"])."<small>"." "."(";
		//			echo $english_format_number = number_format($responseArrayPayments["results"]["bindings"][0]["totalExpenditureLines"]["value"]).")"."</small>";
		
				echo "€ ".$json3['paymentDtls']['amount']."<small>"." "."(";
				echo $json3['paymentDtls']['items'].")"."</small>";
?>
				</h1>
				<div class="stat-percent font-bold text-success"><?php echo $json3['paymentDtls']['variation']; ?>% <i class="fa fa-level-up"></i></div>
                <small></small>
            </div>
        </div>
    </div>
    <div class="col-lg-3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <span class="label label-info pull-right"><?php echo $json3['assignmentDtls']['year']; ?></span>
                <h5>Αναθέσεις Δημοσίου (ΔΙΑΥΓΕΙΑ)</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	//			echo "€ ".$english_format_number = number_format($responseArrayAssignments["results"]["bindings"][0]["totalAmount"]["value"])."<small>"." "."(";
		//			echo $english_format_number = number_format($responseArrayAssignments["results"]["bindings"][0]["totalContracts"]["value"]).")"."</small>";
		
				echo "€ ".$json3['assignmentDtls']['amount']."<small>"." "."(";
				echo $json3['assignmentDtls']['items'].")"."</small>";
?>
				</h1>
				<div class="stat-percent font-bold text-info"><?php echo $json3['assignmentDtls']['variation']; ?>% <i class="fa fa-level-down"></i></div>
                <small></small>
            </div>
        </div>
    </div>
    <div class="col-lg-3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <span class="label label-primary pull-right"><?php echo $json3['espaDtls']['year']; ?></span>
                <h5>Επιδοτήσεις ΕΣΠΑ</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	//			echo "€ ".$english_format_number = number_format($responseArraySubsidies["results"]["bindings"][0]["siAmount"]["value"])."<small>"." "."(";	
		//			echo $english_format_number = number_format($responseArraySubsidies["results"]["bindings"][0]["subsidyCounter"]["value"]).")"."</small>";
		
				echo "€ ".$json3['espaDtls']['amount']."<small>"." "."(";
				echo $json3['espaDtls']['items'].")"."</small>";
?>
				</h1>
				<div></div>
                <small></small>
            </div>
        </div>
    </div>
	
	 <div class="col-lg-3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <span class="label label-primary pull-right"><?php echo $json3['euftsDtls']['year']; ?></span>
                <h5>Πληρωμές Ευρωπαϊκής Ένωσης</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	//			echo "€ ".$english_format_number = number_format($responseArraySubsidies["results"]["bindings"][0]["siAmount"]["value"])."<small>"." "."(";	
		//			echo $english_format_number = number_format($responseArraySubsidies["results"]["bindings"][0]["subsidyCounter"]["value"]).")"."</small>";
		
				echo "€ ".$json3['euftsDtls']['amount']."<small>"." "."(";
				echo $json3['euftsDtls']['items'].")"."</small>";
?>
				</h1>
				<div></div>
                <small></small>
            </div>
        </div>
    </div>
	
	 
	
	
	
	
</div>







<div class="row">

    <div class="col-lg-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5><?php echo  $json2[0]['title'];?></h5>
                            <h2><?php  
							$data = $json2[0]['percentage'];
							echo $data."%";
							?></h2>
                            <div class="progress progress-mini">
                                <div style=<?php
								echo "width:".$data."%;";
								?> class="progress-bar"></div>
                            </div>

                            <div class="m-t-sm small">Συνολικές πληρωμές (ΔΙΑΥΓΕΙΑ 2015): € <?php echo $json2[0]['amount'];?></div>
                        </div>
                    </div>
    </div>
     <div class="col-lg-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5><?php echo  $json2[1]['title'];?></h5>
                            <h2><?php  
							$data = $json2[1]['percentage'];
							echo $data."%";
							?></h2>
                            <div class="progress progress-mini">
                                <div style=<?php
								echo "width:".$data."%;";
								?> class="progress-bar progress-bar-danger"></div>
                            </div>

                            <div class="m-t-sm small">Συνολικές πληρωμές (ΔΙΑΥΓΕΙΑ 2015): € <?php echo $json2[1]['amount'];?></div>
                        </div>
                    </div>
    </div>
    <div class="col-lg-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5><?php echo  $json2[2]['title'];?></h5>
                            <h2><?php  
							$data = $json2[2]['percentage'];
							echo $data."%";
							?></h2>
                            <div class="progress progress-mini">
                                <div style=<?php
								echo "width:".$data."%;";
								?> class="progress-bar"></div>
                            </div>

                            <div class="m-t-sm small">Συνολικές πληρωμές (ΔΙΑΥΓΕΙΑ 2015): € <?php echo $json2[2]['amount'];?></div>
                        </div>
                    </div>
    </div>
	
	
	
	
	
</div>




<div class="row">


					<div class="col-lg-3" ng-controller="metricsCtrl as spark">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5>Πληρωμές Δημοσίου στη <?php 
							$name = $json[1]['name']; //Vodafone
							echo $name ?> <span class="label label-primary pull-right">2015</span> </h5>
                           <div sparkline spark-data=<?php 
							$data = $json[1]['data_graph'];
							$str_data = implode(",",$data);
							echo "[".$str_data."]" ?> spark-options="spark.demo1Options"></div>
                        </div>
                    </div>
					</div>
					
					<div class="col-lg-3" ng-controller="metricsCtrl as spark">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5>Πληρωμές Δημοσίου στη <?php 
							$name = $json[2]['name']; //Forthnet
							echo $name ?>  <span class="label label-primary pull-right">2015</span> </h5>
                            <h2></h2>
                            <div sparkline spark-data=<?php 
							$data = $json[2]['data_graph'];
							$str_data = implode(",",$data);
							echo "[".$str_data."]" ?> spark-options="spark.demo1Options"></div>
                        </div>
                    </div>
					</div>
					
					<div class="col-lg-3" ng-controller="metricsCtrl as spark">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5>Πληρωμές Δημοσίου στη <?php 
							$name = $json[3]['name']; //CYTA
							echo $name ?>  <span class="label label-primary pull-right">2015</span> </h5>
                            <h2></h2>
                            <div sparkline spark-data=<?php 
							$data = $json[3]['data_graph'];
							$str_data = implode(",",$data);
							echo "[".$str_data."]" ?> spark-options="spark.demo1Options"></div>
                        </div>
                    </div>
                </div>

				<div class="col-lg-3" ng-controller="metricsCtrl as spark">
                    <div class="ibox">
                       <div class="ibox-content">
                            <h5>Πληρωμές Δημοσίου στη <?php 
							$name = $json[4]['name']; //Wind
							echo $name ?> <span class="label label-primary pull-right">2015</span> </h5>
                            <h2></h2>
                            <div sparkline spark-data=<?php 
							$data = $json[4]['data_graph'];
							$str_data = implode(",",$data);
							echo "[".$str_data."]" ?> spark-options="spark.demo1Options"></div>
                        </div>
                    </div>
                </div>



</div>




<div class="wrapper wrapper-content animated fadeInRight" ng-controller="chartJs2Ctrl as chart">
    <div class="row">
        <div class="col-lg-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Πληρωμές Δημοσίου στο OTE Group (2015)
                        <small></small>
                    </h5>
                    <div ibox-tools></div>
                </div>
                <div class="ibox-content">
                    <div>
                        <canvas linechart options="chart.lineOptions" data="chart.lineData" height="200" responsive=true ></canvas>
                    </div>
                </div>
            </div>
        </div>
		
		
		<div class="col-lg-3" ng-controller="flotChartCtrl as charts">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Επιδοτήσεις στις Τηλεπικοινωνίες</h5>
					<div ibox-tools></div>
                </div>
                <div class="ibox-content">
                    <div class="flot-chart">
                        <div flot class="flot-chart-content" dataset="charts.flotPieData" options="charts.flotPieOptions"></div>
                    </div>
                </div>
            </div>
		</div>
		
		
		
		
		

    </div>
</div>



<div class="wrapper wrapper-content animated fadeInRight" >
    <div class="row">
        <div class="col-lg-6" ng-controller="chartJs3Ctrl as chart" >
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Social Impact Comparison
                        <small></small>
                    </h5>
                    <div ibox-tools></div>
                </div>
                <div class="ibox-content">
                    <div>
                        <canvas linechart options="chart.lineOptions" data="chart.lineData" height="250" responsive=true ></canvas>
                    </div>
                </div>
            </div>
        </div>
		
		<div class="col-lg-3" ng-controller="chartistCtrl as chart">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Influence Metric </h5>
                </div>
                <div class="ibox-content">
                    <chartist class="ct-chart ct-perfect-fourth" chartist-data="chart.horizontalData" chartist-chart-options="chart.horizontalOptions" chartist-chart-type="Bar"></chartist>
                </div>
            </div>
        </div>


		<div class="col-lg-3" ng-controller="chartistCtrl2 as chart">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Followers</h5>
                </div>
                <div class="ibox-content">
                    <chartist class="ct-chart ct-perfect-fourth" chartist-data="chart.horizontalData" chartist-chart-options="chart.horizontalOptions" chartist-chart-type="Bar"></chartist>
                </div>
            </div>
        </div>
		
		
		
		
		
			

    </div>
</div>



<div class="wrapper wrapper-content animated fadeInRight" >
    <div class="row">
	<div class="col-lg-6">
	
	
	<div>Επεξήγηση χρωμάτων: 
	<span style="color:#008000;">ΟΤΕ</span>
	<span style="color:#ff0000;">VOFADONE</span>
	<span style="color:#ffa500;">FORTHNET</span>
	<span style="color:#ffff00;">CYTA</span>
	<span style="color:#0000ff;">WIND</span>
	</div>
	
	
</div>	
</div>	
</div>



<div class="row">
<div class="col-lg-12">
	
	
	
<div class="news custom en1000" style="color:white" ng-controller="payments2Ctrl">
	
	<ul>
		<li ng-repeat="payment in payments">
            <div>{{ payment.buyer }}</div>
			<div>€ {{ payment.amount }} ({{payment.date}})</div>
			<div><img style="opacity:0.7;filter:alpha(opacity=70); width:90px;" src="logo/cosmote_logo_gr.png" alt="cosmote"></div>
		</li>	
	</ul>
</div>


</div>



				



</div>





<div class="wrapper wrapper-content animated fadeInRight">


	<div class="row">
        <div class="col-lg-12">
            <div class="tabs-container">

                <tabset>
                    <tab heading="Πληρωμές Δημοσίου">
                        <div class="panel-body">
						
    <div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments2Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Ημερομηνία</th>
            <th>Ποσό (€)</th>
            <th>Ανάδοχος</th>
			<th>Πληρωτής</th>
            <th>Κατηγορία Δαπάνης</th>
            <th>Πηγή</th>
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="payment in payments">
            <td>{{ payment.date }}</td>
            <td>{{ payment.amount }}</td>
            <td>{{ payment.seller }}</td>
			<td>{{ payment.buyer }}</td>
            <td>{{ payment.cpv }}</td>
			<td><a href="{{payment.ada_url}}" target=\"_blank\">{{ payment.ada }}</a></td>
        </tr>
        </tbody>
    </table>
	</div>					   
                        </div>
                    </tab>
                    <tab heading="Αναθέσεις Δημοσίου">
                        <div class="panel-body">
                           
						   
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="assignments2Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Ημερομηνία</th>
            <th>Ποσό (€)</th>
            <th>Ανάδοχος</th>
			<th>Κατηγορία Δαπάνης</th>
            <th>Πηγή</th>
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="assignment in assignments">
            <td>{{ assignment.date }}</td>
            <td>{{ assignment.amount }}</td>
            <td>{{ assignment.seller }}</td>
			<td>{{ assignment.cpv }}</td>
			<td><a href="{{assignment.ada_url}}" target=\"_blank\">{{ assignment.ada }}</a></td>
        </tr>
        </tbody>
    </table>
	</div>					   
						   
						   
                        </div>
                    </tab>
					<tab heading="Επιδοτήσεις ΕΣΠΑ">
                        <div class="panel-body">
                            
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="subsidies2Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Θέμα</th>
            <th>Δήμος</th>
            <th>Προϋπολογισμός</th>
			<th>Συμβάσεις</th>
            <th>Πληρωμές</th>
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="subsidy in subsidies">
            <td>{{ subsidy.subject }}</td>
            <td>{{ subsidy.municName }}</td>
            <td>{{ subsidy.biAmount }}</td>
			<td>{{ subsidy.cntrAmount }}</td>
			<td>{{ subsidy.siAmount }}</td>
        </tr>
        </tbody>
    </table>
	</div>					   
							
                        </div>
                    </tab>
					
				
					<tab heading="Πληρωμές Ε/Ε">
                        <div class="panel-body">
                            
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments_ote_eu_Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Ημερομηνία</th>
            <th>Ποσό (€)</th>
            <th>Ανάδοχος</th>
			<th>Πληρωτής</th>
            <th>Περιγραφή</th>
            <th>Αριθμός συμβολαίου</th>
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="payment in payments">
            <td>{{ payment.date }}</td>
            <td>{{ payment.amount }}</td>
            <td>{{ payment.seller }}</td>
			<td>{{ payment.buyer }}</td>
            <td>{{ payment.description }}</td>
			<td>{{ payment.cntrId }}</td>
        </tr>
        </tbody>
    </table>
	</div>					   
							
                        </div>
                    </tab>
					
					
					
					
                </tabset>

            </div>
        </div>
	</div>
	
	
<h4>Σημειώσεις και Πηγές δεδομένων</h4> 

<p>Ο Όμιλος ΟΤΕ αποτελείται από τις εξής εταιρείες: ΟΤΕPLUS, COSMOTE, ΟΤΕ ΑΚΙΝΗΤΑ, ΟΤΕESTATE, ΓΕΡΜΑΝΟΣ, ΕΚΠΑΙΔΕΥΤΙΚΟ ΚΕΝΤΡΟ, ΟΤΕ, ΟΤΕSAT MARITEL, ΟΤΕ GLOBE SA.</p>

<p>Τα δεδομένα πληρωμών, επιδοτήσεων ΕΣΠΑ και ΦΕΚ προέρχονται από το <a href="http://linkedeconomy.org/" target="_blank">http://linkedeconomy.org/</a>. Τα δεδομένα πληρωμών της Ευρωπαϊκής Ένωσης προέρχονται από το <a href="http://ec.europa.eu/budget/fts/index_en.htm" target="_blank">Financial Transparency System</a>.  Η αξιολόγηση των δεδομένων του Twitter προέρχονται από το <a href="http://www.influencetracker.com:8890/sparql" target="_blank">InfluenceTracker SPARQL endpoint</a>. Για την εμφάνιση έχει χρησιμοποιηθεί το <a href="https://wrapbootstrap.com/theme/inspinia-responsive-admin-theme-WB0R5L90S" target="_blank">INSPINIA - Responsive Admin Theme</a>.</p>
	
	

</div>	
	
</body>
</html>