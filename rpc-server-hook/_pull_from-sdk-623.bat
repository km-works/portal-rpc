set TGT=E:\devtools\liferay-portal\plugins-sdk-623-ga4\hooks
set SELF=rpc-server-hook
robocopy %TGT%\%SELF% . /MIR
pause