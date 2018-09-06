<?php
include './baglan.php';
mysql_set_charset('utf8mb4');

$kitap_id = (int) @(htmlspecialchars($_GET['kitap_id']));
$kitap_uyeid = (int) @(htmlspecialchars($_GET['kitap_uyeid']));

$sqlsorgu = mysql_query("SELECT * FROM kitap_table WHERE kitap_id = $kitap_id");
$sqlsorgu2 = mysql_query("SELECT * FROM kullanici_table WHERE uye_id = $kitap_uyeid");
	
	$uye_adi = "";
	$uye_soyad = "";
	while ($r2 = mysql_fetch_assoc($sqlsorgu2)) {
	$uye_adi = (string) $r2['uye_adi'];
	$uye_soyad = (string) $r2['uye_soyad'];
	}
	
	$table = array();
	$table['cols'] = array(
    array('label' => 'kitap_id', 'type' => 'number'),
    array('label' => 'kitap_isbn10', 'type' => 'string'),
	array('label' => 'kitap_isbn13', 'type' => 'string'),
	array('label' => 'kitap_adi', 'type' => 'string'),
	array('label' => 'kitap_aciklama', 'type' => 'string'),
	array('label' => 'kitap_yazar', 'type' => 'string'),
    array('label' => 'kitap_sayfa', 'type' => 'number'),
	array('label' => 'kitap_yayinci', 'type' => 'string'),
	array('label' => 'kitap_tarih', 'type' => 'string'),
	array('label' => 'kitap_kategori', 'type' => 'number'),
	array('label' => 'kitap_dil', 'type' => 'string'),
    array('label' => 'kitap_resim', 'type' => 'string'),
	array('label' => 'kitap_fiyat', 'type' => 'number'),
	array('label' => 'kitap_uyetelefon', 'type' => 'string'),
	array('label' => 'kitap_il', 'type' => 'string'),
	array('label' => 'kitap_ilce', 'type' => 'string'),
	array('label' => 'kitap_ilantarih', 'type' => 'string'),
	array('label' => 'kitap_uyeadi', 'type' => 'string'),
	array('label' => 'kitap_uyesoyadi', 'type' => 'string'),
	);
	
	$rows = array();
	while ($r = mysql_fetch_assoc($sqlsorgu)) {
    $temp = array();
	$temp[] = array('v' => (int) $r['kitap_id']);
	$temp[] = array('v' => (string) $r['kitap_isbn10']);
    $temp[] = array('v' => (string) $r['kitap_isbn13']);
    $temp[] = array('v' => (string) $r['kitap_adi']);
    $temp[] = array('v' => (string) $r['kitap_aciklama']);
	$temp[] = array('v' => (string) $r['kitap_yazar']);
	$temp[] = array('v' => (int) $r['kitap_sayfa']);
    $temp[] = array('v' => (string) $r['kitap_yayinci']);
    $temp[] = array('v' => (string) $r['kitap_tarih']);
    $temp[] = array('v' => (int) $r['kitap_kategori']);
	$temp[] = array('v' => (string) $r['kitap_dil']);
	$temp[] = array('v' => (string) $r['kitap_resim']);
    $temp[] = array('v' => (int) $r['kitap_fiyat']);
	$temp[] = array('v' => (string) $r['kitap_uyetelefon']);
    $temp[] = array('v' => (string) $r['kitap_il']);
	$temp[] = array('v' => (string) $r['kitap_ilce']);
	$temp[] = array('v' => (string) $r['kitap_ilantarih']);
	$temp[] = array('v' => $uye_adi);
	$temp[] = array('v' => $uye_soyad);
    $rows[] = array('c' => $temp);
	}

	$table['rows'] = $rows;
	$jsonTable = json_encode($table);

	
	echo $jsonTable;

?>