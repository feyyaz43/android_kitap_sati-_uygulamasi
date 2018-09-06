<?php
include './baglan.php';
mysql_set_charset('utf8mb4');

$kitap_id = (int) @(htmlspecialchars($_GET['kitap_id']));

$sqlsorgu = mysql_query("DELETE FROM kitap_table WHERE kitap_id=$kitap_id");

if($sqlsorgu){
echo '{"durum":1}';
}
else if(!$sqlsorgu){
echo '{"durum":0}';
}

?>