<!doctype html>
<html>
<head>


</head>

<body>



<?php

//metrics
$ch = curl_init("http://83.212.86.156:83/develop/json/NBG/Banks_Group_daily_payments.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json = json_decode($content, true);
//end metrics


//metrics_usage
$ch = curl_init("http://83.212.86.156:83/develop/json/NBG/metrics_cpv.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json2 = json_decode($content, true);
//end metrics_usage

//ote_group_dtls
$ch = curl_init("http://83.212.86.156:83/develop/json/NBG/Ethniki_Group_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$json3 = json_decode($content, true);
//end ote_group_dtls


?>

<div class="wrapper wrapper-content">
<div class="row">


<div class="col-lg-3">
<h1>NBG Group</h1>

<br>
<br>
<br>
		
			
</div>

<div class="col-lg-3">
<img style="opacity:0.2;filter:alpha(opacity=20); width:200px;" src="logo/nbg_logo.png" alt="nbg" >
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
   
	
	
	
	
	
	
	
	
</div>



<div class="row">

   
	
	
	
	<div class="col-lg-3" ng-controller="metricsCtrl as spark">
                    <div class="ibox">
                        <div class="ibox-content">
                            <h5>Πληρωμές Δημοσίου στη <?php 
							$name = $json[1]['name']; 
							echo $name ?>  <span class="label label-primary pull-right">2015</span> </h5>
                            <h2></h2>
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
							$name = $json[2]['name']; 
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
							$name = $json[3]['name']; 
							echo $name ?> <span class="label label-primary pull-right">2015</span> </h5>
                            <h2></h2>
                            <div sparkline spark-data=<?php 
							$data = $json[3]['data_graph'];
							$str_data = implode(",",$data);
							echo "[".$str_data."]" ?> spark-options="spark.demo1Options"></div>
                        </div>
                    </div>
                </div>
	
	
	
	
</div>




<div class="wrapper wrapper-content animated fadeInRight" ng-controller="chartJs_nbg_yearly_payments_Ctrl as chart">
    <div class="row">
        <div class="col-lg-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Πληρωμές Δημοσίου στο NBG Group (2015)
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
		
		
		
		
			
		
		

    </div>
</div>



<div class="wrapper wrapper-content animated fadeInRight" >
    <div class="row">
        <div class="col-lg-6" ng-controller="chartJs_nbg_social_impact_Ctrl as chart" >
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
		
		<div class="col-lg-3" ng-controller="chartistCtrl_nbg as chart">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Influence Metric </h5>
                </div>
                <div class="ibox-content">
                    <chartist class="ct-chart ct-perfect-fourth" chartist-data="chart.horizontalData" chartist-chart-options="chart.horizontalOptions" chartist-chart-type="Bar"></chartist>
                </div>
            </div>
        </div>


		<div class="col-lg-3" ng-controller="chartistCtrl2_nbg as chart">
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
	<span style="color:#008000;">ibanknbg</span>
	<span style="color:#ff0000;">winbank_tweets</span>
	<span style="color:#ffa500;">alpha_bank</span>
	<span style="color:#ffff00;">eurobank_group</span>
	
	</div>
	
	
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
						
    <div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments_nbg_Ctrl">                      
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
                           
						   
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="assignments_nbg_Ctrl">                      
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
					
		
					
                </tabset>

            </div>
        </div>
	</div>
	
	

</div>	
	
</body>
</html>