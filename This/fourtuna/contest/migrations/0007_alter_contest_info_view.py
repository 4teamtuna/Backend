# Generated by Django 4.2.6 on 2023-12-09 01:59

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ("contest", "0006_remove_contest_info_contents"),
    ]

    operations = [
        migrations.AlterField(
            model_name="contest_info",
            name="view",
            field=models.IntegerField(default=0),
        ),
    ]
