<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <head>
        <meta charset="utf-8">
        <title>UI Design</title>
        <!-- Description, Keywords and Author -->
        <meta name="description" content="Your description">
        <meta name="keywords" content="Your,Keywords">
        <meta name="author" content="ResponsiveWebInc">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Styles -->
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font awesome CSS -->
        <link href="css/font-awesome.min.css" rel="stylesheet">


        <!-- Main CSS -->
        <link href="css/style-318.css" rel="stylesheet">

        <!-- Favicon -->
        <link rel="shortcut icon" href="ui-318.html#">
    </head>

    <body>


    <!-- UI # -->
    <div class="ui-318">

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item One Starts -->
                    <!-- Item Starts -->
                    <div class="db-item db-br-red item-one">
                        <!-- Heading -->
                        <h4>Visitors</h4>
                        <!-- Knob -->
                        <input class="knob" data-angleOffset=-180 data-angleArc=360 data-bgColor="rgba(247,83,83,0.3)" data-fgColor="#f75353" data-thickness=".2" value="90" data-end="90">
                        <!-- Details -->
                        <div class="db-details">
                            <!-- UnOrdered Listing -->
                            <ul class="text-left list-unstyled">
                                <!-- Icon And Value -->
                                <li><i class="fa fa-square red"></i> &nbsp;Today
                                    <b>20%</b></li>
                                <!-- Icon And Value -->
                                <li><i class="fa fa-square green"></i> &nbsp;Yesterday
                                    <b>40%</b></li>
                            </ul>
                        </div>
                    </div>
                    <!-- Item One Ends -->
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Two Starts -->
                    <!-- Item Starts -->
                    <div class="db-item db-br-lblue item-two">
                        <!-- Heading -->
                        <h4>Chart</h4>
                        <!-- Bar Chart -->
                        <span class="bar-chart">15,20,34,56,78,23,90,13,50,20,45</span>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- Value And Icon -->
                            <span> 140 <i class="fa fa-angle-double-up red"></i></span> &nbsp;
                            <!-- Value And Icon -->
                            <span> 250 <i class="fa fa-angle-double-up green"></i></span>
                        </div>
                    </div>
                    <!-- Item Two Ends -->
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Three Starts -->
                    <!-- Item Starts -->
                    <div class="db-item db-br-green item-three">
                        <!-- Heading -->
                        <h4>Social Media</h4>
                        <!-- Social -->
                        <div class="db-social">
                            <!-- UnOrdered Listing -->
                            <ul class="text-left list-unstyled  brand-bg">
                                <!-- Social Media Icons -->
                                <!-- Facebook -->
                                <li><a href="ui-318.html#" class="facebook"><i class="fa fa-facebook"></i></a>&nbsp; Facebook <b>20%</b></li>
                                <!-- Twitter -->
                                <li><a href="ui-318.html#" class="twitter"><i class="fa fa-twitter"></i></a>&nbsp; Twitter <b>40%</b></li>
                                <!-- Linkedin -->
                                <li><a href="ui-318.html#" class="linkedin"><i class="fa fa-linkedin"></i></a>&nbsp; Linkedin <b>30%</b></li>
                                <!-- Google-Plus -->
                                <li><a href="ui-318.html#" class="google-plus"><i class="fa fa-google-plus"></i></a>&nbsp; Google-Plus <b>30%</b></li>
                            </ul>
                        </div>
                    </div>
                    <!-- Item Three Ends -->
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Four Starts -->
                    <!-- Item Starts -->
                    <div class="db-item db-br-rose item-four">
                        <!-- Heading -->
                        <h4>Product</h4>
                        <!-- Big Heading -->
                        <h3>$25,000</h3>
                        <i class="fa fa-angle-double-up bg-yellow arrow"></i>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- UnOrdered Listing -->
                            <ul class="text-left list-unstyled">
                                <!-- Icon and Value -->
                                <li><i class="fa fa-circle red circle"></i> &nbsp;Model
                                    <b>1,22,348 &nbsp;<i class="fa fa-angle-double-up red"></i></b>
                                </li>
                                <!-- Icon and Value -->
                                <li><i class="fa fa-circle green circle"></i> &nbsp;Priority
                                    <b>1,12,000 &nbsp;<i class="fa fa-angle-double-down green"></i></b>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- Item Four Ends -->
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Five Starts -->
                    <!-- Item Starts -->
                    <div class="db-item item-five db-br-purple">
                        <!-- Heading -->
                        <h4>Progress</h4>
                        <!-- Progress Span -->
                        <span>60% Complete</span>
                        <!-- Progress -->
                        <div class="progress">
                            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                <span class="sr-only">40% Complete (success)</span>
                            </div>
                        </div>

                        <!-- Progress Span -->
                        <span>100% Complete</span>
                        <!-- Progress -->
                        <div class="progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                <span class="sr-only">40% Complete (success)</span>
                            </div>
                        </div>

                        <!-- Progress Span -->
                        <span>75% Complete</span>
                        <!-- Progress -->
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 75%">
                                <span class="sr-only">40% Complete (success)</span>
                            </div>
                        </div>

                        <!-- Progress Span -->
                        <span>30% Complete</span>
                        <!-- Progress -->
                        <div class="progress">
                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 30%">
                                <span class="sr-only">40% Complete (success)</span>
                            </div>
                        </div>

                    </div>
                    <!-- Item Five Starts -->
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-six db-br-orange">
                        <!-- Heading -->
                        <h4>Details</h4>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- UnOrdered Listing -->
                            <ul class="text-left list-unstyled">
                                <!-- Text And Value -->
                                <li>Hampden<b>12%</b></li>
                                <li>Sydney<b>72%</b></li>
                                <li>Classical<b>52%</b></li>
                                <li>Finibus<b>92%</b></li>
                                <li>Bonorus<b>32%</b></li>
                            </ul>
                            <a href="ui-318.html#" class="btn btn-green btn-sm">More Details</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-seven db-br-brown">
                        <!-- Big Heading -->
                        <h3>$12,120</h3>
                        <!-- Bar Chart -->
                        <span class="bar-chart-seven">10,19,30,40,50,20,60,55,30,10</span>
                        <!-- Heading -->
                        <h4>Lorem Lipsum</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-eight db-br-blue">
                        <!-- Heading -->
                        <h4>Settings</h4>
                        <!-- Unordered Listing -->
                        <ul class="text-left list-unstyled">
                            <!-- Icon , Link , Button -->
                            <li><i class="fa fa-wifi"></i> &nbsp; Wifi <a href="ui-318.html#" class="btn btn-red btn-xs">Off</a></li>
                            <li><i class="fa fa-envelope"></i> &nbsp; Email <a href="ui-318.html#" class="btn btn-green btn-xs">On</a></li>
                            <li><i class="fa fa-linux"></i> &nbsp; Linux <a href="ui-318.html#" class="btn btn-green btn-xs">On</a></li>
                            <li><i class="fa fa-book"></i> &nbsp; Book <a href="ui-318.html#" class="btn btn-red btn-xs">Off</a></li>
                            <li><i class="fa fa-dropbox"></i> &nbsp; Dropbox <a href="ui-318.html#" class="btn btn-green btn-xs">On</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-nine db-br-red">
                        <!-- Chart -->
                        <div class="db-chart bg-lblue">
                            <!-- Heading -->
                            <h4>Chart</h4>
                            <!-- Bar Chart -->
                            <span class="bar-chart-nine">10,30,40,55,66,20,44,66,20,30,10</span>
                        </div>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- Content One -->
                            <div class="db-content-one">
                                <!-- Big Heading -->
                                <h3>45</h3>
                                <!-- Heading -->
                                <span>Finibus</span>
                            </div>
                            <!-- Content Two -->
                            <div class="db-content-two">
                                <!-- Big Heading -->
                                <h3>67</h3>
                                <!-- Heading -->
                                <span>Hampden</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-ten db-br-green">
                        <!-- Heading -->
                        <h4>Pie Chart</h4>
                        <!-- Chart -->
                        <div class="db-chart">
                            <!-- Pie Chart -->
                            <span class="ten-pie">13,28,35,16</span>
                        </div>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- Content One -->
                            <div class="db-content-one">
                                <!-- Big Heading And Icon -->
                                <h3>4,425 <i class="fa fa-caret-up yellow"></i></h3>
                                <!-- Heading -->
                                <span>Finibus</span>
                            </div>
                            <!-- Content Two -->
                            <div class="db-content-two">
                                <!-- Big Heading And Icon -->
                                <h3>6,867 <i class="fa fa-caret-down yellow"></i></h3>
                                <!-- Heading -->
                                <span>Hampden</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-eleven db-br-orange">
                        <!-- Heading -->
                        <h4>Ratio</h4>
                        <!-- Big Heading -->
                        <h3><span class="lblue">$</span>3,450</h3>
                        <hr />
                        <!-- Icon and Text -->
                        <span class="red"> 45.43% <i class="fa fa-caret-down black"></i></span>
                        <span class="green"> 56.22% <i class="fa fa-caret-up black"></i></span>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 col-xs-6 col-mob">
                    <!-- Item Starts -->
                    <div class="db-item item-twelve">
                        <!-- Icon -->
                        <div class="db-icon bg-lblue">
                            <!-- Heading -->
                            <h4 class="white">Recorder</h4>
                            <!-- Icon -->
                            <i class="fa fa-caret-up"></i>
                        </div>
                        <!-- Details -->
                        <div class="db-details">
                            <!-- Big Number -->
                            <span>$12,000</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- Javascript files -->
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- jQuery Knob -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jQuery-Knob/1.2.13/jquery.knob.min.js"></script>
    <!-- Sparklines -->
    <script src="js/sparklines.js"></script>
    <!-- Placeholder JS -->
    <script src="js/placeholder.js"></script>
    <!-- Respond JS for IE8 -->
    <script src="http://cdn.example.com/js/respond.min.js?2013012401"></script>
    <!-- HTML5 Support for IE -->
    <script src="js/html5shiv.js"></script>

    <script>

        <!-- Item First -->
        <!-- Knob -->
        $(function() {
            $(".knob").knob({
                width: 100,  // Width
                height: 100, //Height
                readOnly: true //ReadOnly
            });
        });

        <!-- Item Two -->
        $(function() {

            /* Bar sparklines */
            $(".bar-chart").sparkline("html", {type: 'bar',height: '100',lineColor: '#32c8de',barWidth: '10',barColor: '#ff61e7'});

        });

        <!-- Item Seven -->
        $(function() {

            /* Bar sparklines */
            $(".bar-chart-seven").sparkline("html", {type: 'bar',height: '100',lineColor: '#32c8de',barWidth: '10',barColor: '#f75353'});

        });

        <!-- Item Nine -->
        $(function() {

            /* Bar sparklines */
            $(".bar-chart-nine").sparkline("html", {type: 'bar',height: '90',lineColor: '#32c8de',barWidth: '10',barColor: '#fff'});

        });

        <!-- Item Ten -->
        $(function() {

            /* Pie sparklines */
            $('.ten-pie').sparkline("html",{type: "pie",width: "100",height: "120",sliceColors:["#ed5441","#609cec","#51d466","#fcd419"]});

        });

        <!-- Status Seven -->
        $(function() {

            /* Bar sparklines */
            $('.seven-bar-one').sparkline("html",{type: "bar", barWidth:"4",barColor:"#ff0000", height: "65"});

            $('.seven-bar-two').sparkline("html",{type: "bar", barWidth:"4",barColor:"#32c8de", height: "65"});

        });

    </script>

    </body>
    </html>

</@c.page>