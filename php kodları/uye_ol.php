<?php

$uye_mail = (htmlspecialchars($_GET['uyeolMail']));
$uye_adi = (htmlspecialchars($_GET['uyeolAd']));
$uye_soyad = (htmlspecialchars($_GET['uyeolSoyad']));
$uye_sifre = (htmlspecialchars($_GET['uyeolSifre']));

include './baglan.php';
mysql_set_charset('utf8mb4');

$uye_uyelik_tarih = date("d.m.Y");

$mail_icerik = "";
$mail_icerik .= "Kitap Uygulaması için bilgileriniz : "."\n";
$mail_icerik .= "mail adresiniz : ".(string)$uye_mail."\n";
$mail_icerik .= "adınız : ".(string)$uye_adi."\n";
$mail_icerik .= "soyadınız : ".(string)$uye_soyad."\n";
$mail_icerik .= "şifreniz : ".(string)$uye_sifre."\n";
$mail_icerik .= "üyelik tarihi : ".(string)$uye_uyelik_tarih."\n";

$konu = "Bilgilendirme";

$gonderici = "from: feyyazfy@gmail.com\r\n";
$gonderici .= "reply-to: feyyazfy@gmail.com\r\n";

$ayni_mail_sorgu = mysql_query("select * from kullanici_table where uye_mail='".$uye_mail."'");

if(mysql_num_rows($ayni_mail_sorgu) == 0){
	$sqlsorgu = mysql_query("insert into kullanici_table(uye_adi , uye_soyad , uye_mail , uye_sifre , uye_uyelik_tarih) values ('".$uye_adi."','".$uye_soyad."','".$uye_mail."','".$uye_sifre."','".$uye_uyelik_tarih."')");
	if($sqlsorgu && mail($uye_mail, $konu, $mail_icerik, $gonderici)){
		echo '{"durum":1}';
	}
	else if(!$sqlsorgu){
		echo '{"durum":0}';
	}
}else{
	echo '{"durum":0}';
}



?>