<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SonarQube report</title>
    <#--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="src/main/resources/static/bootstrap.min.css">
    <style>
        body{
            background: #ffffff;
            color: #333333;
            font-family: "HelvRegularIBM", "Helvetica Neue", Arial, sans-serif;
            margin: 2% 3%;
        }
    </style>
</head>
<body>
<h2><b>SonarQube Report</b></h2>
<div class="row justify-content-md-left">
    <table class="table table-sm" style="width: 80%; margin-left: 1%">
        <tbody>
        <tr>
            <td><b>Datе of creating report:</b> ${date}</td>
            <td><b>Project name:</b> ${project.name}</td>
        </tr>
        <tr>
            <td><b>Date of last project analysis</b>: ${project.lastAnalysisDate}</td>
            <td><b>Project Key:</b> ${project.key}</td>
        </tr>
        </tbody>
    </table>
</div>
<br>

<h3>Total Summary</h3>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <div class="card text-white bg-secondary mb-3" style="max-width: 12rem; text-align: center">
                <div class="card-header">Total issues</div>
                <div class="card-body">
                    <h3 class="card-title">${projectIssues.total}</h3>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card text-white bg-danger mb-3" style="max-width: 12rem;text-align: center">
                <div class="card-header">Bug</div>
                <div class="card-body">
                    <h3 class="card-title">${countBugs}</h3>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card text-white bg-warning mb-3" style="max-width: 12rem;text-align: center">
                <div class="card-header">Vulnerability</div>
                <div class="card-body">
                    <h3 class="card-title">${countVulnerabilities}</h3>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card text-white bg-info mb-3" style="max-width: 12rem;text-align: center">
                <div class="card-header">Code Smell</div>
                <div class="card-body">
                    <h3 class="card-title">${countCodeSmells}</h3>
                </div>
            </div>
        </div>
        <div class="col-sm"></div>
    </div>
</div>
<br>
<h3>Issue Severity Summary</h3>
<table class="table">
    <thead>
    <tr>
        <th scope="col">SYMBOL</th>
        <th scope="col">LABEL</th>
        <th scope="col" style="width: 200px;">TOTAL RESULTS</th>
        <th scope="col">DESCRIPTION</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td style="text-align: center"><img src="src/main/resources/static/png/blocker.png" alt="BLOCKER" width="30">
        </td>
        <td>BLOCKER</td>
        <td style="text-align: center">${countBlocker}</td>
        <td>Bug with a high probability to impact the behavior of the application in production. The code MUST be
            immediately fixed.
        </td>
    </tr>
    <tr>
        <td style="text-align: center"><img src="src/main/resources/static/png/critical.png" alt="CRITICAL" width="30">
        </td>
        <td>CRITICAL</td>
        <td style="text-align: center">${countCritical}</td>
        <td>Either a bug with a low probability to impact the behavior of the application in production or an issue
            which represents a security flaw. The code MUST be immediately reviewed.
        </td>
    </tr>
    <tr>
        <td style="text-align: center"><img src="src/main/resources/static/png/major.png" alt="MAJOR" width="30"></td>
        <td>MAJOR</td>
        <td style="text-align: center">${countMajor}</td>
        <td>Quality flaw which can highly impact the developer productivity.</td>
    </tr>
    <tr>
        <td style="text-align: center"><img src="src/main/resources/static/png/minor.png" alt="MINOR" width="30"></td>
        <td>MINOR</td>
        <td style="text-align: center">${countMinor}</td>
        <td>Quality flaw which can slightly impact the developer productivity.</td>
    </tr>
    <tr>
        <td style="text-align: center"><img src="src/main/resources/static/png/info.png" alt="INFO" width="30"></td>
        <td>INFO</td>
        <td style="text-align: center">${countInfo}</td>
        <td>Neither a bug nor a quality flaw, just a finding.</td>
    </tr>
    </tbody>
</table>
<br>

<#list issueList as i>
    <div class="card bg-light mb-3" style="max-width: 90%;">
        <div class="card-header">${i.component}</div>
        <div class="card-body">
            <h6 class="card-title"><b>${i.message}</b></h6>
            <p class="card-text">
            <div class="container">
                <div class="row justify-content-md-left">
                    <div class="col-md-auto">
                        <#if i.type == 'CODE_SMELL'>
                            <img src="src/main/resources/static/png/codesm.png" alt="CODE_SMELL" width="18">
                            Code Smell
                        <#elseif i.type == 'VULNERABILITY'>
                            <img src="src/main/resources/static/png/vulnerability.png" alt="VULNERABILITY" width="18">
                            Vulnerability
                        <#elseif i.type == 'BUG'>
                            <img src="src/main/resources/static/png/bug.png" alt="BUG" width="18">
                            Bug
                        </#if>
                    </div>
                    <div class="col-md-auto">
                        <#if i.severity == 'BLOCKER'>
                            <img src="src/main/resources/static/png/blocker.png" alt="BLOCKER" width="18">
                            Blocker
                        <#elseif i.severity == 'CRITICAL'>
                            <img src="src/main/resources/static/png/critical.png" alt="CRITICAL" width="18">
                            Critical
                        <#elseif i.severity == 'MAJOR'>
                            <img src="src/main/resources/static/png/major.png" alt="MAJOR" width="18">
                            Major
                        <#elseif i.severity == 'MINOR'>
                            <img src="src/main/resources/static/png/minor.png" alt="MINOR" width="18">
                            Minor
                        <#elseif i.severity == 'INFO'>
                            <img src="src/main/resources/static/png/info.png" alt="INFO" width="18">
                            Info
                        </#if>
                    </div>
                    <div class="col-md-auto">
                        ${i.status}
                    </div>
                    <div class="col-md-auto">
                        ${i.author}
                    </div>
                    <div class="col-md-auto">
                        ${i.effort} effort
                    </div>
                    <div class="col-md-auto">
                        <#if i.line != 0>
                            Line:${i.line}
                        </#if>
                    </div>
                    <div class="col-md-auto">
                        Created: ${i.creationDate}
                    </div>
                    <div class="col-md-auto">
                        <#if i.creationDate != i.updateDate>
                            Updated: ${i.updateDate}
                        </#if>
                    </div>
                </div>
            </div>
            </p>
            <div class="alert alert-secondary" role="alert">
                <b>See rule:</b><br>
                ${i.ruleName.name}<br>
                ${i.ruleName.htmlDesc}<br>
                Language: ${i.ruleName.langName}<br>
            </div>
        </div>
    </div>
    <br>
</#list>
</body>
</html>