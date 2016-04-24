<!doctype html>
<html>
<head>


<style>


</style>
</head>

<body>



<?php
//ORACLE_group_dtls
$ch = curl_init("http://83.212.86.156:83/develop/json/ORACLE/Oracle_Payment_All_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$oracle1 = json_decode($content, true);


//ORACLE Diavgeia
$ch = curl_init("http://83.212.86.156:83/develop/json/ORACLE/Oracle_Payment_Diavgeia_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$oracle2 = json_decode($content, true);

//ORACLE Europe
$ch = curl_init("http://83.212.86.156:83/develop/json/ORACLE/Oracle_Payment_Eufts_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$oracle3 = json_decode($content, true);

//ORACLE Australia
$ch = curl_init("http://83.212.86.156:83/develop/json/ORACLE/Oracle_Payment_Australia_dtls.json"); // add your url which contains json file
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$content = curl_exec($ch);
curl_close($ch);
$oracle4 = json_decode($content, true);

?>

<div class="wrapper wrapper-content">


<div class="row">


<div class="col-lg-3">
<img style="opacity:0.4;filter:alpha(opacity=40); width:290px;" src="logo/oracle_logo.gif" alt="oracle" >
</div>


    <div class="col-lg-9">
		
		
		
		
    </div>


</div>
</div>


<div class="wrapper wrapper-content">
<div class="row">

       <div class="col-lg-3">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <span class="label label-primary pull-right"></span>
                <h5>Oracle Group</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php		
	
		
				echo "€ ".$oracle1['paymentDtls']['amountOverall']."<small>"." "."(";
				echo $oracle1['paymentDtls']['itemsOverall'].")"."</small>";
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
                <span class="label label-primary pull-right"><?php echo $oracle2['paymentDtls']['year']; ?></span>
                <h5>Oracle Ελλάδα</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	
	
		
				echo "€ ".$oracle2['paymentDtls']['totalAmount']."<small>"." "."(";
				echo $oracle2['paymentDtls']['items'].")"."</small>";
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
                <span class="label label-primary pull-right"><?php echo $oracle3['paymentDtls']['year']; ?></span>
                <h5>Oracle Ε.Ένωση</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php		
		
		
				echo "€ ".$oracle3['paymentDtls']['totalAmount']."<small>"." "."(";
				echo $oracle3['paymentDtls']['items'].")"."</small>";
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
                <span class="label label-primary pull-right"><?php echo $oracle4['paymentDtls']['year']; ?></span>
                <h5>Oracle Αυστραλία</h5>
            </div>
            <div class="ibox-content">
                <h1 class="no-margins">
<?php	
		
		
				echo "$ ".$oracle4['paymentDtls']['totalAmount']."<small>"." "."(";
				echo $oracle4['paymentDtls']['items'].")"."</small>";
?>
				</h1>
				<div></div>
                <small></small>
            </div>
        </div>
    </div>
	
	
	
	
</div>









<div class="wrapper wrapper-content animated fadeInRight">


	<div class="row">
        <div class="col-lg-12">
            <div class="tabs-container">

                <tabset>
                    <tab heading="Πληρωμές Ελλάδα">
                        <div class="panel-body">
						
    <div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments_oracle_diavgeia_Ctrl">                      
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
                    <tab heading="Πληρωμές Αυστραλία">
                        <div class="panel-body">
                           
						   
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments_oracle_australia_Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Ημερομηνία</th>
            <th>Ποσό (AUS $)</th>
            <th>Ανάδοχος</th>
			<th>Περιγραφή</th>
           
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="payment in payments">
            <td>{{ payment.date }}</td>
            <td>{{ payment.amount }}</td>
            <td>{{ payment.seller }}</td>
			<td>{{ payment.description }}</td>
			
        </tr>
        </tbody>
    </table>
	</div>					   
						   
						   
                        </div>
                    </tab>
					<tab heading="Πληρωμές Ευρώπη">
                        <div class="panel-body">
                            
	<div class="wrapper wrapper-content animated fadeInRight" ng-controller="payments_oracle_europe_Ctrl">                      
	<table datatable="ng" dt-options="dtOptions"   dt-column-defs="dtColumnDefs" class="table table-striped table-bordered table-hover dataTables-example">
					
					
        <thead>
        <tr>
            <th>Έτος</th>
            <th>Ποσό (€)</th>
            <th>Ανάδοχος</th>
			<th>Περιγραφή</th>
		</tr>
        </thead>
        <tbody>
		
        <tr ng-repeat="payment in payments">
            <td>{{ payment.date }}</td>
            <td>{{ payment.amount }}</td>
            <td>{{ payment.seller }}</td>
			<td>{{ payment.description }}</td>
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