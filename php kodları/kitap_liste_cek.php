<?php
include './baglan.php';
mysql_set_charset('utf8mb4');

$ival = (int) @(htmlspecialchars($_GET['ilk']));
$load_limit = (int) @(htmlspecialchars($_GET['load_limit']));
$isbn = (string) @(htmlspecialchars($_GET['isbn']));
$kelime = (string) @(htmlspecialchars($_GET['kelime']));
$il = (string) @(htmlspecialchars($_GET['il']));
$ilce = (string) @(htmlspecialchars($_GET['ilce']));
$spinner_secim_kategori_pos = (int) @(htmlspecialchars($_GET['spinner_secim_kategori_pos']));
$spinner_secim_siralama_pos = (int) @(htmlspecialchars($_GET['spinner_secim_siralama_pos']));

$aranan = "%" . $kelime . "%";

$Kategori = array("Tüm Kategoriler","Bilim & Teknik","Çizgi Roman","Çocuk Kitapları",
            "Din","Edebiyat","Ekonomi & İş Dünyası","Felsefe & Düşünce",
            "Hukuk","Osmanlıca","Referans & Başvuru","Sağlık",
            "Sanat","Sınav ve Ders Kitapları","Spor","Tarih",
            "Toplum & Siyaset","Diğer & Çeşitli");
			
$FiyataGore = array("Sıralama Yok","Fiyata Göre Azalan","Fiyata Göre Artan");

$sql_sorgu_cumle = "SELECT kitap_id,kitap_adi,kitap_resim,kitap_fiyat,kitap_uyeid,kitap_il,kitap_ilce";
$sql_sorgu_cumle .= " FROM kitap_table";

if(!empty($isbn) || !empty($kelime) || !empty($il) || !empty($ilce) || $spinner_secim_kategori_pos != 0 ){
	$sql_sorgu_cumle .= " WHERE";
	
	if(!empty($isbn)){
		$sql_sorgu_cumle .= " kitap_isbn13=";
		$sql_sorgu_cumle .= "'$isbn'";
		if( !empty($kelime) || !empty($il) || !empty($ilce) || $spinner_secim_kategori_pos != 0 ){
			$sql_sorgu_cumle .= " AND";
		}
	}
	
	if(!empty($kelime)){
		$sql_sorgu_cumle .= " kitap_adi LIKE ";
		$sql_sorgu_cumle .= "'$aranan'";
		if( !empty($il) || !empty($ilce) || $spinner_secim_kategori_pos != 0 ){
			$sql_sorgu_cumle .= " AND";
		}
	}
	
	if(!empty($il)){
		$sql_sorgu_cumle .= " kitap_il=";
		$sql_sorgu_cumle .= "'$il'";
		if( !empty($ilce) || $spinner_secim_kategori_pos != 0 ){
			$sql_sorgu_cumle .= " AND";
		}
	}
	
	if(!empty($ilce)){
		$sql_sorgu_cumle .= " kitap_ilce=";
		$sql_sorgu_cumle .= "'$ilce'";
		if( $spinner_secim_kategori_pos != 0 ){
			$sql_sorgu_cumle .= " AND";
		}
	}
	
	if($spinner_secim_kategori_pos != 0){
		$sql_sorgu_cumle .= " kitap_kategori=";
		$sql_sorgu_cumle .= $spinner_secim_kategori_pos;
	}
}


if($spinner_secim_siralama_pos == 1){
	$sql_sorgu_cumle .= " ORDER BY kitap_fiyat DESC";
}
if($spinner_secim_siralama_pos == 2){
	$sql_sorgu_cumle .= " ORDER BY kitap_fiyat";
}

$sql_sorgu_cumle .= " LIMIT $ival,$load_limit";



$sqlsorgu = mysql_query($sql_sorgu_cumle);
	
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