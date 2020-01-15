FROM fabric8/java-centos-openjdk11-jre
ADD target/invoice-0.0.1-SNAPSHOT.jar /home/
CMD ["java","-jar","/home/invoice-0.0.1-SNAPSHOT.jar"]