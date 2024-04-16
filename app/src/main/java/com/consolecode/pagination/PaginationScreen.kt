package com.consolecode.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview
@Composable
fun PaginationScreen(context: MainActivity,viewModel: PostViewModel = viewModel()) {
    val posts = viewModel.posts.collectAsState()
    val loading by viewModel.loading
    val lazyListState = rememberLazyListState()

    val scope = rememberCoroutineScope()
    val coroutineScope = rememberCoroutineScope()

    Box(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(  modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)){
        Text(
            text = "Pagination",
            fontSize = 30.sp,modifier = Modifier.weight(.6f)
        )
            Button(onClick = { context.finish() }) {
                Text(text = "Exit")
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(.5F),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            reverseLayout = false,state = lazyListState,
        )
        {
            itemsIndexed(posts.value) { index, post ->
                PostItem(post)
                if (index == posts.value.size - 1 && !loading) {
                    viewModel.loadNextPage()
                }
            }
            // Add a loading indicator at the end of the list
            if (loading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

    }
       // LoadMoreButton(onClick = viewModel::loadNextPage)
        LoadDutton (onClick ={
            coroutineScope.launch {
                lazyListState.animateScrollToItem(lazyListState.firstVisibleItemScrollOffset)
            }}
            )
    }
}


@Composable
fun PostItem(post: PostData?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(  modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = this.size.maxDimension
                    )
                },
            text = "${post!!.id}")
            Column(){

                Text(text = "Title: ${post.title}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp,
                )
                Text(text = "Body: ${post!!.body}",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(10.dp),
                fontSize = 14.sp,)
            }

        }
    }
}

//@Composable
//fun LoadMoreButton(onClick: () -> Unit) {
//    Box(modifier = Modifier.fillMaxSize()) {
//    FloatingActionButton(
//        modifier = Modifier
//            .padding(30.dp)
//            .size(50.dp)
//            .align(Alignment.BottomEnd)
//            .offset(x = 16.dp, y = 16.dp),
//        onClick = onClick,
//        shape = CircleShape,
//
//    ) {Icon(Icons.Filled.KeyboardArrowDown, "")
//        Spacer(modifier = Modifier.height(16.dp))
//        //Text(text = "More")
//    }
//    }
//}

@Composable
fun LoadDutton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(30.dp)
                .size(50.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 16.dp, y = 16.dp),
            onClick = onClick,
            shape = CircleShape,

            ) {Icon(Icons.Filled.KeyboardArrowDown, "")
            Spacer(modifier = Modifier.height(16.dp))
            //Text(text = "More")
        }
    }
}