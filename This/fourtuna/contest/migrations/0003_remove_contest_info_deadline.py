# Generated by Django 4.2.6 on 2023-12-02 14:10

from django.db import migrations


class Migration(migrations.Migration):
    dependencies = [
        ("contest", "0002_contest_info_keyword_contest_info_link_and_more"),
    ]

    operations = [
        migrations.RemoveField(
            model_name="contest_info",
            name="deadline",
        ),
    ]
