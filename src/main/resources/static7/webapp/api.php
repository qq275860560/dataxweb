<?php
	$apiurl=$_POST["apiurl"];
	$data=$_POST["data"];
    $url = 'http://192.168.0.113:803/index.php/'.$apiurl;
    $res = request_post($url, $data);       
    print_r($res);

	/**
     * ģ��post����url����
     * @param string $url
     * @param array $post_data
     */
    function request_post($url = '', $post_data = array()) {
        if (empty($url) || empty($post_data)) {
            return false;
        }
        $o = "";
        foreach ( $post_data as $k => $v ) 
        { 
            $o.= "$k=" . urlencode( $v ). "&" ;
        }
        $post_data = substr($o,0,-1);

        $postUrl = $url;
        $curlPost = $post_data;
        $ch = curl_init();//��ʼ��curl
        curl_setopt($ch, CURLOPT_URL,$postUrl);//ץȡָ����ҳ
        curl_setopt($ch, CURLOPT_HEADER, 0);//����header
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);//Ҫ����Ϊ�ַ������������Ļ��
        curl_setopt($ch, CURLOPT_POST, 1);//post�ύ��ʽ
        curl_setopt($ch, CURLOPT_POSTFIELDS, $curlPost);
        $data = curl_exec($ch);//����curl
        curl_close($ch);
        return $data;
    }
?>