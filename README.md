# OpenSrcSW

###### 건국대학교 오픈소스 SW 입문 (2021 1학기) 

## kuir 
main 클래스 각각의 옵션들로 다른 클래스를 실행해준다.
<br/>

## Week01 03/04
git 생성 및 초기화
<br/><br/>

## Week02 03/11
### makeCollection 
html 파일들을 하나의 xml 파일로 만드는 프로그램
##### 결과물
- collection.xml
##### 실행방법
-  java kuir -c [./data]
<br/><br/>
   

## Week03 03/18
### makeKeyword
collection.xml을 형태소를 분석하는 프로그램 <br/>
kkma 라이브러리 사용
##### 결과물
- index.xml
##### 사용법
- java kuir -k [./collection.xml]
<br/><br/>
  

## Week04 03/25
### indexer
index.xml에 있는 데이터로 키워드 별 문서 당 가중치를 계산하는 프로그램.

가중치는 TF-IDF(Term Frequency Inverse Document Frequency)
##### 결과물
- index.post (역파일 형태)
##### 사용법
- java kuir -i [./index.xml]
##### post파일 확인방법
- kuir파일에 `indexer.PostOpen();` 주석해제
<br/><br/>

 
## Week05 04/01
### searcher
- index.post를 가져와 query를 입력하여 문서 간의 유사도를 계산하여 입력한 query와 가장 유사한 문서 중 상위 3개의 문서의 title을 출력
- 만약 유사도가 동일시 id가 빠른순으로 출력
##### 결과물
- 따로 없음
##### 사용법
- java kuir -s [./index.post] -q "쿼리문"
<br/><br/>


## Week06, Week07 04/08,15
### searcher
- 기존에 feature 브랜치에서 했던 calcSim 함수를 InnerProduct 변경후 master 브랜치에서 calcSim 함수 생성
- 기존에는 저번주는 내적곱으로 계산, 이번주는 Cosine Similarity 로 계산
- ![image](https://user-images.githubusercontent.com/55578730/115100377-2542ee00-9f77-11eb-8d82-a2ca39d36b71.png)

##### 결과물
- 따로 없음
##### 사용법
- java kuir -s [./index.post] -q "쿼리문"
<br/><br/>


## Week09 04/29
### Python 예제문제 2개
<br/><br/>


## Week10 05/06
### Python 예제문제 3개
<br/><br/>

## Week11 05/13
### Python 예제문제 4개
<br/><br/>

## Week12 05/20
### 데이터분석 예제문제 2개
<br/><br/>

## Week13 05/27
### 데이터분석 예제문제 2개
<br/><br/>

## Week14 06/03
### 데이터분석 예제문제 1개
- 광진구와 가장 비슷, 다른 인구 구조를 가진 구를 찾아 그래프로 시각화
<br/><br/>
