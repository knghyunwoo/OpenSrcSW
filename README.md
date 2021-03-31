# OpenSrcSW

###### 건국대학교 오픈소스 SW 입문 (2021 1학기) 

## kuir 
main 클래스 각각의 옵션들로 다른 클래스를 실행해준다.
<br/>

##Week01 03/04
git 생성 및 초기화
<br/><br/>

##Week02 03/11
###makeCollection 
html 파일들을 하나의 xml 파일로 만드는 프로그램
##### 결과물
- collection.xml
##### 실행방법
-  java kuir -c [./data]
<br/><br/>
   

##Week03 03/18
###makeKeyword
collection.xml을 형태소를 분석하는 프로그램 <br/>
kkma 라이브러리 사용
##### 결과물
- index.xml
##### 사용법
- java kuir -k [./collection.xml]
<br/><br/>
  

##Week04 03/25
###indexer
index.xml에 있는 데이터로 키워드 별 문서 당 가중치를 계산하는 프로그램.

가중치는 TF-IDF(Term Frequency Inverse Document Frequency)
##### 결과물
- index.post (역파일 형태)
##### 사용법
- java kuir -i [./index.xml]
##### post파일 확인방법
- kuir파일에 `indexer.PostOpen();` 주석해제


 