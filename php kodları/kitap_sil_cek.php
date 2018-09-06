<?php
include './baglan.php';
mysql_set_charset('utf8mb4');

$uye_id = (int) @(htmlspecialchars($_GET['uye_id']));

$sqlsorgu = mysql_query("SELECT kitap_id,kitap_adi,kitap_resim,kitap_fiyat,kitap_uyeid,kitap_il,kitap_ilce FROM kitap_table WHERE kitap_uyeid=$uye_id");
	
	$table = array();
	$table['cols'] = array(
    array('label' => 'kitap_id', 'type' => 'number'),
	array('label' => 'kitap_adi', 'type' => 'string'),
    array('label' => 'kitap_resim', 'type' => 'string'),
	array('label' => 'kitap_fiyat', 'type' => 'number'),
	array('label' => 'kitap_uyeid', 'type' => 'number'),
	array('label' => 'kitap_il', 'type' => 'string'),
	array('label' => 'kitap_ilce', 'type' => 'string')
	);
	
	$rows = array();
	while ($r = mysql_fetch_assoc($sqlsorgu)) {
    $temp = array();
	$temp[] = array('v' => (int) $r['kitap_id']);
    $temp[] = array('v' => (string) $r['kitap_adi']);
	$temp[] = array('v' => (string) $r['kitap_resim']);
    $temp[] = array('v' => (int) $r['kitap_fiyat']);
	$temp[] = array('v' => (int) $r['kitap_uyeid']);
    $temp[] = array('v' => (string) $r['kitap_il']);
	$temp[] = array('v' => (string) $r['kitap_ilce']);
    $rows[] = array('c' => $temp);
	}

	$table['rows'] = $rows;
	$jsonTable = json_encode($table);

	echo $jsonTable;

?>