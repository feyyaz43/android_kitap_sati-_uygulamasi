<?php
include './baglan.php';
mysql_set_charset('utf8mb4');

$uye_id = (int) @(htmlspecialchars($_GET['uye_id']));

$sqlsorgu = mysql_query("SELECT * FROM kullanici_table WHERE uye_id = $uye_id");

$sqlsorgu2 = mysql_query("SELECT kitap_id FROM kitap_table WHERE kitap_uyeid = $uye_id");
$ilan_sayisi = mysql_num_rows($sqlsorgu2);
	
	$table = array();
	$table['cols'] = array(
    array('label' => 'uye_id', 'type' => 'number'),
    array('label' => 'uye_adi', 'type' => 'string'),
	array('label' => 'uye_soyad', 'type' => 'string'),
	array('label' => 'uye_mail', 'type' => 'string'),
	array('label' => 'uye_sifre', 'type' => 'string'),
	array('label' => 'uye_uyelik_tarih', 'type' => 'string'),
	array('label' => 'uye_ilan_sayisi', 'type' => 'number')
	);
	
	$rows = array();
	while ($r = mysql_fetch_assoc($sqlsorgu)) {
    $temp = array();
	$temp[] = array('v' => (int) $r['uye_id']);
	$temp[] = array('v' => (string) $r['uye_adi']);
    $temp[] = array('v' => (string) $r['uye_soyad']);
    $temp[] = array('v' => (string) $r['uye_mail']);
	$temp[] = array('v' => (string) $r['uye_sifre']);
	$temp[] = array('v' => (string) $r['uye_uyelik_tarih']);
	$temp[] = array('v' => $ilan_sayisi);
    $rows[] = array('c' => $temp);
	}

	$table['rows'] = $rows;
	$jsonTable = json_encode($table);

	
	echo $jsonTable;

?>