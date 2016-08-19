node {
   // Mark the code checkout 'stage'....
   stage 'Checkout'

   // Get some code from a GitHub repository
   git url: 'https://github.com/rbraam/vanenapp.git'

   // Get the maven tool.
   def mvnHome = tool 'Maven 3.2.5'

   // Mark the code build 'stage'....
   stage 'Build'
   // Run the maven build
   sh "${mvnHome}/bin/mvn -f vanenapp/pom.xml clean package"
}