import os
import datetime
from django.shortcuts import render
from rest_framework.response import Response
from .models import Contest_info, my_contest
from rest_framework.views import APIView
from .serializers import Contest_Info_serializer, My_Contest_serializer
from rest_framework.viewsets import ModelViewSet
from django.core.files import File
from openpyxl import load_workbook 

# Create your views here.

def save_image_from_crawl(image_folder, contest_info):
    image_path = os.path.join(image_folder, f"{contest_info.title}.jpg")
    with open(image_path, 'rb') as img_file:
        contest_info.image.save(f"{contest_info.title}.jpg", File(img_file))
        contest_info.save()

def upload_xlsx_and_images(xlsx_path, image_folder):
    workbook = load_workbook(filename=xlsx_path)
    sheet = workbook.active
    headers = [cell.value for cell in sheet[1]]  # xlsx 파일의 첫 번째 행은 헤더라고 가정합니다.

    for row in sheet.iter_rows(min_row=2):  # 첫 번째 행(헤더)를 제외하고 각 행을 처리합니다.
        row_dict = {headers[i]: cell.value for i, cell in enumerate(row)}

        # 특정 필드만 처리합니다.
        selected_fields = ['title', 'link', 'keyword', 'host', 'date','period', 'view']
       
        if not row_dict.get('title'):
            continue
        row_dict = {key: value for key, value in row_dict.items() if key in selected_fields}
        
        if 'date' not in row_dict or row_dict['date'] is None:
            row_dict['date'] = datetime.datetime.now()
        row_dict['view'] = row_dict.get('view') or '0'
        # 딕셔너리의 값으로 Contest_info 인스턴스를 생성합니다.
        contest_info = Contest_info(**row_dict)
        contest_info.save()  # DB에 저장합니다.

        # 이미지를 저장합니다.
        save_image_from_crawl(image_folder, contest_info)
            
            
#xlsx_path = '/Users/KimDongHyeon/Desktop/KMU/2023 Computer Engineering/3-2/캡스톤디자인/크롤링/공모전.xlsx'
#image_folder = '/Users/KimDongHyeon/Desktop/KMU/2023 Computer Engineering/3-2/캡스톤디자인/Backend/Backend/This/fourtuna/static/contest_img'

class Contest_Info_API(ModelViewSet):
    upload_xlsx_and_images(xlsx_path,image_folder)
    queryset = Contest_info.objects.all()
    serializer_class = Contest_Info_serializer

class My_Contest_API(APIView):
    def get(self, request):
        queryset = my_contest.objects.all()
        print(queryset)
        serializer = My_Contest_serializer(queryset, many = True)
        return Response(serializer.data)
    

