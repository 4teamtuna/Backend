# Generated by Django 4.2.6 on 2023-12-02 14:26

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ("contest", "0003_remove_contest_info_deadline"),
    ]

    operations = [
        migrations.AlterField(
            model_name="contest_info",
            name="image",
            field=models.CharField(max_length=500, verbose_name="이미지"),
        ),
    ]
