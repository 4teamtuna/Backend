from django.core.management.base import BaseCommand
from ...views import upload_xlsx_and_images

class Command(BaseCommand):
    help = 'Upload xlsx data and images'

    def handle(self, *args, **options):
        xlsx_path = '/Users/KimDongHyeon/Desktop/공모전.xlsx'
        image_folder = '/Users/KimDongHyeon/Desktop/KMU/2023 Computer Engineering/3-2/캡스톤디자인/Backend/Backend/This/fourtuna/static/contest_img'
        upload_xlsx_and_images(xlsx_path, image_folder)
