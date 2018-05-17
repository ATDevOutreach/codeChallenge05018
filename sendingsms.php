<?php

extract($_POST);


$file = fopen('content.txt','w');
 
 $from = $_POST['from']; 
 $to = $_POST['to'];
 $text = $_POST['text'];
 $date = $_POST['date'];
 $id = $_POST['id'];
 $linkId = $_POST['linkId']; //This works for onDemand subscription products
  

if (isset($_POST['from'])){

                       
$recipients = $from;

require_once('AfricasTalkingGateway.php');


$username   = "sandbox";
$apikey     = "6b7b601dd903a4b361c672ef47cd6d49eb98297f4510c2d4177ac6d9393b2951";

$message    = "I am a lumberjack. I sleep all night and work all day!";


$from = "4218";

$gateway    = new AfricasTalkingGateway($username, $apikey);

try 
{
   
   $results = $gateway->sendMessage($recipients, $message,$from);
			
  foreach($results as $result) {
    echo " Number: " .$result->number;
    echo " Status: " .$result->status;
    echo " MessageId: " .$result->messageId;
    echo " Cost: "   .$result->cost."\n";
  }
}
catch ( AfricasTalkingGatewayException $e )
{
  echo "Encountered an error while sending: ".$e->getMessage();
}

}
       
?>
