import africastalking
from django.http import HttpResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt


# Create your views here.
@csrf_exempt
def home(request):
    if request.method == "POST":
        receiver = request.POST['from']
        response = request.POST['text']

        africastalking.initialize(username='sandbox', 
                                    api_key='1b8f9dba586f83194b35504d7c5a6a738957020e51a71fa83559eb5f9917078d')
        sms = africastalking.SMS
        sms.send(message='I am a lumberjack. I sleep all night and work all day!', recipients=[receiver], sender_id=57466)
        return HttpResponse(status=200)